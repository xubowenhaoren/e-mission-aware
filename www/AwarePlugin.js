var exec = require('cordova/exec');

//window.sum = function(num1, num2, successCallback, errorCallback) {
//	cordova.exec(successCallback, errorCallback, "AwarePlugin", "sum", [num1, num2]);
//};

window.checkBattery = function(successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, "AwarePlugin", "checkBattery");
};

window.manualSync = function(successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, "AwarePlugin", "manualSync");
};

window.joinStudy = function(pid, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AwarePlugin", "joinStudy", [pid]);
};

window.displayDeviceId = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AwarePlugin", "displayDeviceId");
};

window.displayPid = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AwarePlugin", "displayPid");
};