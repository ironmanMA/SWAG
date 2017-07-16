package com.swiggy.swag.swagapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView test_service_status;
    Button setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        test_service_status = (TextView) findViewById(R.id.test_service_status);
        setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        boolean isSet = AccessibilityServiceUtil.isAccessibilityServiceOn(
                getApplicationContext(),
                "com.swiggy.swag.swagapp",
                "com.swiggy.swag.swagapp.TestService");

        if (isSet) {
            test_service_status.setText(R.string.test_service_on);
        } else {
            test_service_status.setText(R.string.test_service_off);
        }

        super.onResume();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                Intent accessibilityServiceIntent
                        = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                //PackageManager pm = getPackageManager();
                //Intent intentWht=pm.getLaunchIntentForPackage(WhtNotificationService.PACKAGE_NAME);

                startActivity(accessibilityServiceIntent);
                break;
        }
    }
}

