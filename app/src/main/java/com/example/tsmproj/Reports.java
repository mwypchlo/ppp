package com.example.tsmproj;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Reports extends AppCompatActivity {
    SquatSQLHelper squatdb;
    DeadliftSQLHelper deadliftdb;
    ChestpressSQLHelper chestpressdb;
    TextView totalkg;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        totalkg = (TextView) findViewById(R.id.totalkg);
        squatdb = new SquatSQLHelper(this);
        chestpressdb = new ChestpressSQLHelper(this);
        deadliftdb = new DeadliftSQLHelper(this);
        loadtotal();

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        adView = findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("9442458B390263EE43184EE2C8380F2D")
                .build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }
    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    public void loadtotal(){
        int maxsquat = squatdb.getHighestVal();
        int maxbenchpress = chestpressdb.getHighestVal();
        int maxdeadlift=  deadliftdb.getHighestVal();
        totalkg.setText(Integer.toString(maxsquat+maxbenchpress+maxdeadlift) + " kg");
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
