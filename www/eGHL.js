'use strict';

var argscheck = require('cordova/argscheck'),
    exec      = require('cordova/exec');

var eGHL = function() {
    var name = "eGHL Cordova Plugin";
    var version = "v0.1"
}

eGHL.prototype = {
    makePayment: function(params, success, error)
    {
        argscheck.checkArgs('ofF', 'eGHL.makePayment', arguments);
        exec(success, error, 'eGHL', 'makePayment', [params]);
    }
};

module.exports = new eGHL();
