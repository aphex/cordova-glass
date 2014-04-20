package com.rossgerbasi.cordova.plugins.glass;

import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class Glass extends CordovaPlugin implements View.OnGenericMotionListener {
    private ArrayList<GlassMotionEventManager> managers;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
//        Log.d("Glass", "Init Glass Core Plugin");
        super.initialize(cordova, webView);

        // List of all Motion Event Managers
        this.managers = new ArrayList<GlassMotionEventManager>();

        //Test for Keep Awake Preference
        boolean keepAwake = webView.getProperty("rossgerbasi.glass.keepAwake", "false").equals("true");
        if(keepAwake) {
            cordova.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        // Test for Touch Disabling
        boolean touchDisabled = webView.getProperty("rossgerbasi.glass.touchDisabled", "false").equals("true");
        if (!touchDisabled) {
            managers.add(new GlassTouchManager(this.webView));
        }

        // Test for Gesture Disabling
        boolean gesturesDisabled = webView.getProperty("rossgerbasi.glass.gesturesDisabled", "false").equals("true");
        if (!gesturesDisabled) {
            managers.add(new GlassGestureManager(this.webView));
        }

        this.webView.setOnGenericMotionListener(this);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("get_launch_params")) {
            ArrayList<String> voiceResults = this.cordova.getActivity().getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
            JSONArray jsArray;
            if (voiceResults != null) {
                jsArray = new JSONArray(voiceResults);
            } else {
                jsArray = new JSONArray();
            }
            PluginResult result = new PluginResult(PluginResult.Status.OK, jsArray);
            callbackContext.sendPluginResult(result);
        }

        return super.execute(action, args, callbackContext);
    }

    @Override
    public boolean onGenericMotion(View v, MotionEvent event) {
        for(GlassMotionEventManager manager : managers){
            manager.process(event);
        }
        return true;
    }
}
