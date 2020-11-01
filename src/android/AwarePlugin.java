package edu.berkeley.eecs.emission.cordova.aware;

import org.apache.cordova.BuildConfig;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.Manifest;
//import androidx.core.content.PermissionChecker;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.providers.Aware_Provider;

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
                Manifest.permission.READ_SYNC_STATS,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
        };
        cordova.requestPermissions(this, 0, permissions);
        Log.d(ctxt, TAG, "permissions for Aware OK");
        Aware.setSetting(cordova.getActivity().getApplicationContext(),
                Aware_Preferences.DEBUG_FLAG, "true");
        Aware.setSetting(cordova.getActivity().getApplicationContext(),
                Aware_Preferences.DEBUG_TAG, TAG + "- Aware");
        try {
            JSONObject installedVersion = new JSONObject();
            installedVersion.put("package_installed", ctxt.getPackageName());
            installedVersion.put("version_name", BuildConfig.VERSION_NAME);
            installedVersion.put("version_code", BuildConfig.VERSION_CODE);
            AwareDebug(installedVersion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("checkBattery")) {
            // TODO move this to joinStudy
            checkBatteryOptimization();
            callbackContext.success("checked battery");
            return true;
        } else if (action.equals("manualSync")) {
            return manualSyncAction(callbackContext);
        } else if (action.equals("joinStudy")) {
            String pid = args.getString(0).trim();
            Log.d(ctxt, TAG, "AWARE - join study got pid:" + pid);
            return joinStudyAction(pid, callbackContext);
        } else if (action.equals("displayDeviceId")) {
            return displaySomeIDAction(action, callbackContext);
        } else if (action.equals("displayPid")) {
            return displaySomeIDAction(action, callbackContext);
        } else if (action.equals("exitStudy")) {
            return exitStudyAction(callbackContext);
        }
        return false;
    }

    public void startAwareServiceHelper() {
        if (!Aware.IS_CORE_RUNNING) {
            StartAware();
            Log.d(ctxt, TAG, "StartAware OK? " + Aware.IS_CORE_RUNNING);
        } else {
            Log.d(ctxt, TAG, "Aware already started");
        }
    }

    private boolean manualSyncAction(CallbackContext callbackContext) {
        startAwareServiceHelper();
        Log.d(ctxt, TAG, "manual syncing...");
        if (Aware.isStudy(ctxt)) {
            Log.d(ctxt, TAG, Aware.getAWAREAccount(ctxt).toString());
            Log.d(ctxt, TAG, Aware_Provider.getAuthority(ctxt));
            syncNow();
            callbackContext.success("Started syncing, stay plugged in for the next 5 min.");
        } else {
            Log.d(ctxt, TAG, "AWARE - do not sync, not in study");
            callbackContext.success("You are not in a study yet.");
        }
        return true;
    }

    private boolean joinStudyAction(String pid, CallbackContext callbackContext) {
        startAwareServiceHelper();
        Log.d(ctxt, TAG, "joinStudy called from UI");
        // Check if we already joined a study
        if (Aware.isStudy(ctxt)) {
            Log.d(ctxt, TAG, "AWARE - join study NOT needed, already in study");
            callbackContext.success("You are already in a study.");
        } else {
            if (pid.length() < 3 || pid.equals("null") || pid == null) {
                Log.d(ctxt, TAG, "AWARE - join study NOT OK, PID too short");
                callbackContext.success("NOT OK, PID too short");
            } else {
                joinStudy(pid);
                Log.d(ctxt, TAG, "AWARE - join study OK");
                callbackContext.success("Join OK");
            }
        }
        return true;
    }

    private boolean exitStudyAction(CallbackContext callbackContext) {
        Log.d(ctxt, TAG, "exitStudy called from UI");
        // Check if we already joined a study
        if (Aware.isStudy(ctxt)) {
            String studyUrl = "https://slicomex.cs.washington.edu/index.php/webservice/index/23/wzNlNqjjDEyz";
            Cursor study = Aware.getStudy(ctxt, studyUrl);
            if (study != null && study.moveToFirst()) {
                ContentValues studyData = new ContentValues();
                studyData.put(Aware_Provider.Aware_Studies.STUDY_JOINED, 0);
                studyData.put(Aware_Provider.Aware_Studies.STUDY_EXIT, 1);
                ctxt.getContentResolver().update(Aware_Provider.Aware_Studies.CONTENT_URI, studyData, Aware_Provider.Aware_Studies.STUDY_URL + " LIKE '" + studyUrl + "'", null);
            }
            if (study != null && !study.isClosed()) study.close();

            // Now, we shut down all Aware sensors.
            Aware.quitStudy(ctxt);
            if (Aware.isStudy(ctxt)) {
                callbackContext.success("Exit study FAILED!");
            }
            StopAware();
            callbackContext.success("Exit study success!");
        } else {
            // We are not in a study.
            Log.d(ctxt, TAG, "AWARE - exit study NOT needed, already out of study");
            StopAware();
            callbackContext.success("You are not in a study.");
        }
        return true;
    }

    private boolean displaySomeIDAction(String idType, CallbackContext callbackContext) {
        startAwareServiceHelper();
        if (Aware.isStudy(ctxt)) {
            Log.d(ctxt, TAG, idType + " called from UI");
            if (idType.equals("displayDeviceId")) {
                String deviceID = Aware.getSetting(cordova.getActivity().getApplicationContext(), Aware_Preferences.DEVICE_ID);
                Log.d(ctxt, TAG, "The device id is" + deviceID);
                callbackContext.success(deviceID);
            } else {
                String PID = Aware.getSetting(cordova.getActivity().getApplicationContext(), Aware_Preferences.DEVICE_LABEL);
                String message = PID + " (Already in study)";
                Log.d(ctxt, TAG, message);
                callbackContext.success(message);
            }
        } else {
            Log.d(ctxt, TAG, "AWARE - " + idType + " NOT needed, not in study");
            callbackContext.success("You are not in a study yet.");
        }
        return true;
    }

    private void checkBatteryOptimization() {
        startAwareServiceHelper();
        if (!Aware.isStudy(ctxt)) {
            StartAware();
            Log.d(ctxt, TAG, "StartAware OK? " + Aware.IS_CORE_RUNNING);
        }
        Log.d(ctxt, TAG, "checking battery");
        Applications.isAccessibilityServiceActive(ctxt.getApplicationContext());
        Aware.isBatteryOptimizationIgnored(ctxt.getApplicationContext(),
                ctxt.getApplicationContext().getPackageName());
        Log.d(ctxt, TAG, "Is study: " + Aware.isStudy(ctxt)
                + ", is core running: " + Aware.IS_CORE_RUNNING);
    }


    class JoinStudyTask extends AsyncTask<Void,Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            Aware.joinStudy(cordova.getActivity().getApplicationContext(),
                    "https://slicomex.cs.washington.edu/index.php/webservice/index/23/wzNlNqjjDEyz");
            return null;
        }
    }

    private void joinStudy(String pid) {
        checkBatteryOptimization();
        Aware.setSetting(cordova.getActivity().getApplicationContext(),
                Aware_Preferences.DEVICE_LABEL, pid);
        new JoinStudyTask().execute();
    }

    private void StartAware(){
        new StartAwareTask().execute();
    }

    private void StopAware(){
        new StopAwareTask().execute();
    }

    private void syncNow() {
        new SyncNowTask().execute();
    }

    class SyncNowTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            Intent syncNow = new Intent(Aware.ACTION_AWARE_SYNC_DATA);
            ctxt.sendBroadcast(syncNow);
            return null;
        }
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

    class StopAwareTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            Intent aware = new Intent(ctxt, Aware.class);
            ctxt.stopService(aware);
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
