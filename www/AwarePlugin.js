var exec = require('cordova/exec');

//var Aware = {
//    getConfig: function () {
//        return new Promise(function(resolve, reject) {
//            exec(resolve, reject, "Aware", "getConfig", []);
//        });
//    },
//    setConfig: function (newConfig) {
//        return new Promise(function(resolve, reject) {
//            exec(resolve, reject, "Aware", "setConfig", [newConfig]);
//        });
//    },
//}

//window.sum = function(num1, num2, successCallback, errorCallback) {
//	cordova.exec(successCallback, errorCallback, "AwarePlugin", "sum", [num1, num2]);
//};

window.checkBattery = function(successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, "AwarePlugin", "checkBattery");
};

window.manualSync = function(successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, "AwarePlugin", "manualSync");
};

window.joinStudy = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AwarePlugin", "joinStudy");
};

window.displayDeviceId = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AwarePlugin", "displayDeviceId");
};