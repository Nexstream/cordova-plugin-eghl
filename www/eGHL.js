'use strict';

var argscheck = require('cordova/argscheck'),
    exec      = require('cordova/exec');

var eGHL = function() {
    this.name = 'eGHL Cordova Plugin';
    this.version = '3.0.5'
};

eGHL.prototype = {
    makePayment: function(params, success, error)
    {
        argscheck.checkArgs('ofF', 'eGHL.makePayment', arguments);
        exec(
            success,
            function (err) {
                if(typeof err != 'string') error(err)
                else {
                    // eGHL Android SDK's TxnMessage is escaped.
                    err = decodeURIComponent(err)
                    err = err.replace(/\+/g, " ")
                    error(err)
                }
            },
            'eGHL',
            'makePayment',
            [params]
        );
    },

    mpeRequest: function (params, success, error)
    {
        argscheck.checkArgs('ofF', 'eGHL.mpeRequest', arguments);
        exec(function (resp) {
            if(cordova.platformId == 'android') {
                // Android returns a string. We need to parse it ourselves.
                // In fact after parsing, we may find this is an error response.
                // SAMPLE SUCCESS:
                // - "ReqToken=312312io321io3uio12&PairingToken=3213123l1jlkj\r\n"
                // - "{\"preCheckoutId\":\"...\", ..." JSON
                // - * 1st MPE Response (not paired yet) seems to always return
                //     key-val pairs. On the other hand, 1st EC Response (already
                //     paired) seems to return JSON strings...
                // SAMPLE ERROR:
                // - ... HTML page with an embedded "href=\"?x=&y=&TxnStatus=1&TxnMessage=some message...\"...
                try { success(parseAndroidResponse(resp+'')) } // ensure resp is a string
                catch(e) { error(e.message) }
            } else {
                success(resp);
            }
        }, error, 'eGHL', 'mpeRequest', [params]);
    },
};

module.exports = new eGHL();


// Internal helpers ------------------------------------------------------------

function trim (s)
{
    var matches = s.match(/^\s*(.*?)\s*$/);
    if(matches === null) return '';
    else return matches[1];
};

function keyValStringToObject (s)
{
    var variables = {};
    trim(s)
    .split('&')
    .forEach(function (part) {
        if(part != '') {
            var keyVal = part.split('=');
            var key, val;
            if(keyVal.length == 1) {
                key = part;
                val = true;
            } else {
                key = decodeURIComponent(keyVal[0]);
                val = decodeURIComponent(keyVal.slice(1).join('='));
            }
            variables[key] = val;
        }
    })
    return variables;
};

function parseAndroidResponse (s)
{
    if(s.indexOf('html') >= 0 && /href=".*[?&]TxnMessage=?/.test(s)) {
        // Error
        var txnMessage = s.match(/[?&]TxnMessage=([^"&]*)/);
        if(txnMessage) throw new Error(txnMessage[1]);
        else throw new Error();
    } else {
        try {
            // Android SDK returns JSON string when user is already paired.
            return JSON.parse(s);
        } catch (e) {
            // But Android SDK returns key-val pair string when user is NOT paired.
            return keyValStringToObject(s);
        }
    }
};
