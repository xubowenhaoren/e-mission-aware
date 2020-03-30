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
import edu.berkeley.eecs.emission.cordova.AwarePlugin.Applications;
import edu.berkeley.eecs.emission.cordova.AwarePlugin.Aware;
import edu.berkeley.eecs.emission.cordova.AwarePlugin.Aware_Preferences;
import edu.berkeley.eecs.emission.cordova.AwarePlugin.ui.PermissionsHandler;
import edu.berkeley.eecs.emission.cordova.AwarePlugin.providers.Aware;

public class AwarePlugin extends CordovaPlugin {
    private ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>();
    public static final String TAG = "AwarePlugin";

    @Override
    public void pluginInitialize() {
        REQUIRED_PERMISSIONS.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        REQUIRED_PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);
        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        REQUIRED_PERMISSIONS.add(Manifest.permission.ACCESS_NETWORK_STATE);
        REQUIRED_PERMISSIONS.add(Manifest.permission.READ_CALL_LOG);
        REQUIRED_PERMISSIONS.add(Manifest.permission.READ_CONTACTS);
        REQUIRED_PERMISSIONS.add(Manifest.permission.READ_SMS);
        REQUIRED_PERMISSIONS.add(Manifest.permission.READ_PHONE_STATE);
//        REQUIRED_PERMISSIONS.add(Manifest.permission.RECORD_AUDIO);
        REQUIRED_PERMISSIONS.add(Manifest.permission.BLUETOOTH);
        REQUIRED_PERMISSIONS.add(Manifest.permission.BLUETOOTH_ADMIN);

        REQUIRED_PERMISSIONS.add(Manifest.permission.GET_ACCOUNTS);
        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_SYNC_SETTINGS);
        REQUIRED_PERMISSIONS.add(Manifest.permission.READ_SYNC_SETTINGS);
        REQUIRED_PERMISSIONS.add(Manifest.permission.READ_SYNC_STATS);

        IntentFilter filter = new IntentFilter(Aware.ACTION_JOINED_STUDY);
        registerReceiver(joinListener, filter);
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