package com.example.anant.injdet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Button transit = (Button)findViewById(R.id.transit);
        /*
        transit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent m1 = new Intent(v.getContext(), Person.class);
                startActivity(m1);
            }
        });
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("woof","started app");

        Button transit = (Button)findViewById(R.id.transit);
        transit.setOnClickListener(buttonClickListener);

        Button help = (Button)findViewById(R.id.Help);
        help.setOnClickListener(buttonClickListener);

    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.transit) {
                Intent m1 = new Intent(v.getContext(), Person.class);
                startActivity(m1);
            }else if(v.getId() == R.id.Help){
                Intent m2 = new Intent(v.getContext(),Helper.class);
                startActivity(m2);
            }
        }
    };

    /**
     *
     <uses-permission android:name="android.permission.CAMERA" />
     <uses-feature android:name="android.hardware.camera" />
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     ...
     !-- Needed only if your app targets Android 5.0 (API level 21) or higher. -->
     <uses-feature android:name="android.hardware.location.gps" />

     */
}
