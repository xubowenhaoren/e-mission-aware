/*global cordova, module*/

var exec = require("cordova/exec")

var Aware = {
    getConfig: function () {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, "Aware", "getConfig", []);
        });
    },
    setConfig: function (newConfig) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, "Aware", "setConfig", [newConfig]);
        });
    },
}