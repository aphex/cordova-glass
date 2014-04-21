package com.rossgerbasi.cordova.plugins.glass;

import android.util.Log;
import android.view.MotionEvent;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;
import org.json.JSONObject;

public class GlassGestureManager extends GlassMotionEventManager implements GestureDetector.BaseListener, GestureDetector.FingerListener, GestureDetector.ScrollListener, GestureDetector.TwoFingerScrollListener{
    private final GestureDetector mGestureDetector;

    public GlassGestureManager(CordovaWebView webView) {
        super(webView);
        mGestureDetector = new GestureDetector(this.webView.getContext());
        mGestureDetector.setBaseListener(this);
        mGestureDetector.setFingerListener(this);
        mGestureDetector.setScrollListener(this);
        mGestureDetector.setTwoFingerScrollListener(this);

    }

    public void process(MotionEvent event) {
        mGestureDetector.onMotionEvent(event);
    }

    @Override
    public boolean onGesture(Gesture gesture) {
        String type = gesture.toString().toLowerCase().replace("_","");
        this.fireEvent(type, null);
//        Log.d("Glass", type);
        return false;
    }

    @Override
    public void onFingerCountChanged(int i, int i2) {
        JSONObject data = new JSONObject();
        try {
            data.put("from", i);
            data.put("to", i2);
        } catch (JSONException e) {
            Log.d("Glass", "Exception setting FingerCountChanged data");
        }

        this.fireEvent("fingercountchanged", data);
    }

    @Override
    public boolean onScroll(float v, float v2, float v3) {
        JSONObject data = new JSONObject();
        try {
            data.put("displacement", v);
            data.put("delta", v2);
            data.put("velocity", v3);
        } catch (JSONException e) {
            Log.d("Glass", "Exception setting inScroll data");
        }

        this.fireEvent("scroll", data);
        return false;
    }

    @Override
    public boolean onTwoFingerScroll(float v, float v2, float v3) {
        JSONObject data = new JSONObject();
        try {
            data.put("displacement", v);
            data.put("delta", v2);
            data.put("velocity", v3);
        } catch (JSONException e) {
            Log.d("Glass", "Exception setting onTwoFingerScroll data");
        }

        this.fireEvent("twofingerscroll", data);
        return false;
    }

    private void fireEvent(String type, JSONObject data) {
        if (data == null) {
            data = new JSONObject();
        }

        try {
            data.put("type", type);
        } catch (JSONException e) {
            Log.d("Glass", "Exception setting type on event data");
        }

        String js = "javascript:try{cordova.fireDocumentEvent('"+type+"'" + (data != null  ? "," + data : "") +" );}catch(e){console.log('exception firing gesture event from native');};";
        webView.loadUrl(js);
        //Log.d("Glass", "Sent: " + type + " with: " + data);
    }
}