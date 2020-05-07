package edu.berkeley.eecs.emission.cordova.aware;

import org.apache.cordova.BuildConfig;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.Manifest;
//import androidx.core.content.PermissionChecker;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

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
    private Context ctxt;

    @Override
    public void pluginInitialize() {
        ctxt = cordova.getActivity().getApplicationContext();
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
        Log.d(ctxt, TAG, "permissions for Aware OK");
//        AwareService service = new AwareService();
//        service.start();
        Aware.setSetting(cordova.getActivity().getApplicationContext(),
                Aware_Preferences.DEVICE_LABEL, "Bowen - 0502");
        if (!Aware.isStudy(ctxt)) {
            joinStudy();
            Log.d(ctxt, TAG, "joinStudy OK");
        }

//        else {
//          Log.d(ctxt, TAG, "checking battery");
//          Applications.isAccessibilityServiceActive(ctxt.getApplicationContext());
//          Aware.isBatteryOptimizationIgnored(ctxt.getApplicationContext(),
//            ctxt.getApplicationContext().getPackageName());
//        }
        try {
            JSONObject installedVersion = new JSONObject();
            installedVersion.put("package_installed", ctxt.getPackageName());
            installedVersion.put("version_name", BuildConfig.VERSION_NAME);
            installedVersion.put("version_code", BuildConfig.VERSION_CODE);
            AwareDebug(installedVersion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!Aware.IS_CORE_RUNNING) {
            StartAware();
            Log.d(ctxt, TAG, "StartAware OK? " + Aware.IS_CORE_RUNNING);
        } else {
            Log.d(ctxt, TAG, "Aware already started");
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("sum")) {
            Integer num1 = args.getInt(0);
            Integer num2 = args.getInt(1);
            this.sum(num1, num2, callbackContext);
            return true;
        } else if (action.equals("checkBattery")) {
            Log.d(ctxt, TAG, "checking battery");
            Applications.isAccessibilityServiceActive(ctxt.getApplicationContext());
            Aware.isBatteryOptimizationIgnored(ctxt.getApplicationContext(),
                    ctxt.getApplicationContext().getPackageName());
            Log.d(ctxt, TAG, "Is study: " + Aware.isStudy(ctxt)
                    + ", is core running: " + Aware.IS_CORE_RUNNING);
//            if (!Aware.isStudy(ctxt)) {
//              joinStudy();
//              Log.d(ctxt, TAG, "joinStudy OK");
//            }
            callbackContext.success("checked battery");
            return true;
        }

        return false;

    }


    private void sum(Integer num1, Integer num2, CallbackContext callbackContext) {
        if(num1 != null && num2 != null) {
            callbackContext.success(num1 + num2);
        } else {
            callbackContext.error("Expected two integer arguments.");
        }
    }

    class JoinStudyTask extends AsyncTask<Void,Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            Aware.joinStudy(cordova.getActivity().getApplicationContext(),
                    "https://slicomex.cs.washington.edu/index.php/webservice/index/12/c0MlRvLaJPKJ");
            return null;
        }
    }

    private void joinStudy() {
        new JoinStudyTask().execute();
    }

    private void StartAware(){
        new StartAwareTask().execute();
    }

    class StartAwareTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            Intent aware = new Intent(ctxt, Aware.class);
            ctxt.startService(aware);
            return null;
        }
    }

    private void AwareDebug(JSONObject JsOb) {
        new AwareDebugTask().execute(JsOb);
    }

    class AwareDebugTask extends AsyncTask<JSONObject, Void, Void>
    {
        @Override
        protected Void doInBackground(JSONObject... jsonObjects) {
            Aware.debug(ctxt, jsonObjects.toString());
            return null;
        }
    }
}
