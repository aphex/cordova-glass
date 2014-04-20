package com.rossgerbasi.cordova.plugins.glass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import java.util.ArrayList;

public class GoogleGlassActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = this.getPackageManager().getLaunchIntentForPackage(this.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        ArrayList<String> voiceResults = this.getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
        intent.putExtra(RecognizerIntent.EXTRA_RESULTS, voiceResults);
        this.startActivity(intent);
    }
}