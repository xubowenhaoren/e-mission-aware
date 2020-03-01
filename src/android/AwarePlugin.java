package edu.berkeley.eecs.emission.cordova.AwarePlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AwarePlugin extends CordovaPlugin {
    public static final String TAG = "AwarePlugin";

    @Override
    public void pluginInitialize() {
        AwareService service = new AwareService();
        service.start();
    }

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("getConfig")) {
            // call AWARE code to get settings
        } else if (action.equals("setConfig")) {
            // call AWARE code to set settings
        }
    }
}