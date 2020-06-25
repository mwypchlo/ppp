package com.example.tsmproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Dashboard extends AppCompatActivity {
    ImageButton squatButton, reportsButton, benchButton, deadliftButton;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        squatButton = (ImageButton) findViewById(R.id.squatButton); //Squat
        deadliftButton = (ImageButton) findViewById(R.id.deadliftButton);
        benchButton = (ImageButton) findViewById(R.id.benchpressButton);
        reportsButton = (ImageButton) findViewById(R.id.reportsButton); //Reports

        squatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSquat();
            }
        });
        benchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openBench();
            }
        });
        deadliftButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDeadlift();
            }
        });
        reportsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openReports();
            }
        });

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
    public void openSquat(){
        Intent intent = new Intent(this,Squat.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void openReports(){
        Intent intent = new Intent(this,Reports.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void openBench(){
        Intent intent = new Intent(this,Chestpress.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openDeadlift(){
        Intent intent = new Intent(this,Deadlift.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
