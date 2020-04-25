package edu.berkeley.eecs.emission.cordova.aware.AwarePlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.Manifest;
//import android.support.v4.content.PermissionChecker;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import edu.berkeley.eecs.emission.cordova.unifiedlogger.Log;

import java.util.ArrayList;

/**
 * This class echoes a string called from JavaScript.
 */
public class AwarePlugin extends CordovaPlugin {
//    private ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>();
    public static final String TAG = "AwarePlugin";

    @Override
    public void pluginInitialize() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_SYNC_SETTINGS,
                Manifest.permission.READ_SYNC_SETTINGS,
                Manifest.permission.READ_SYNC_STATS
        };
        cordova.requestPermissions(this, 0, permissions);
        Log.d(cordova.getActivity(), TAG, "permissions for Aware OK");
//        AwareService service = new AwareService();
//        service.start();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            pluginInitialize();
            return true;
        }

        if (action.equals("performAdd")) {
            int arg1 = args.getInt(0);
            int arg2 = args.getInt(1);
            /* Indicating success is failure is done by calling the appropriate method on the
            callbackContext.*/
            int result = arg1 + arg2;
            callbackContext.success("result calculated in Java: " + result);
            return true;
        }

        if (action.equals("manual_sync")) {
            // manual sync ops here
        }
        return false;

    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
