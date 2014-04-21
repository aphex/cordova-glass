package com.rossgerbasi.cordova.plugins.glass;

import android.view.MotionEvent;
import org.apache.cordova.CordovaWebView;

abstract class GlassMotionEventManager {
    protected CordovaWebView webView;

    public GlassMotionEventManager (CordovaWebView webView) {
        this.webView = webView;
    }

    abstract void process (MotionEvent event);
}