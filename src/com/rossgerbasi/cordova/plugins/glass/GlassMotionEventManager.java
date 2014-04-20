package com.rossgerbasi.cordova.plugins.glass;

import android.view.MotionEvent;
import android.webkit.WebView;

abstract class GlassMotionEventManager {
    protected WebView webView;

    public GlassMotionEventManager (WebView webview) {
        this.webView = webview;
    }

    abstract void process (MotionEvent event);
}