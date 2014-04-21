package com.rossgerbasi.cordova.plugins.glass;

import android.view.InputDevice;
import android.view.MotionEvent;
import org.apache.cordova.CordovaWebView;

public class GlassTouchManager extends GlassMotionEventManager {
    public GlassTouchManager(CordovaWebView webView) {
        super(webView);
    }

    public void process(MotionEvent event) {
        // Map TouchPad space into WebView space
        Float rangeX = InputDevice.getDevice(event.getDeviceId()).getMotionRange(MotionEvent.AXIS_X).getRange();
        Float rangeY = InputDevice.getDevice(event.getDeviceId()).getMotionRange(MotionEvent.AXIS_Y).getRange();
        Float screenX = (event.getAxisValue(MotionEvent.AXIS_X)/rangeX) * this.webView.getWidth();
        Float screenY = (event.getAxisValue(MotionEvent.AXIS_Y)/rangeY) * this.webView.getHeight();
        event.setLocation(screenX, screenY);

        // Dispatch Touch event on WebView
        this.webView.dispatchTouchEvent(event);
    }
}