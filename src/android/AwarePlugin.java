package edu.berkeley.eecs.emission.cordova.AwarePlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.Manifest;
import android.support.v4.content.PermissionChecker;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;

public class AwarePlugin extends CordovaPlugin {
    private ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>();
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
//        AwareService service = new AwareService();
//        service.start();
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