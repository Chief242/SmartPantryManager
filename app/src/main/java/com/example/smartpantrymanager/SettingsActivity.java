package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchAlerts;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_settings
        );

        switchAlerts =
                findViewById(
                        R.id.switchAlerts
                );

        preferences =
                getSharedPreferences(
                        "smart_pantry_settings",
                        MODE_PRIVATE
                );

        boolean alertsEnabled =
                preferences.getBoolean(
                        "alerts_enabled",
                        true
                );

        switchAlerts.setChecked(
                alertsEnabled
        );

        switchAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences
                            .edit()
                            .putBoolean(
                                    "alerts_enabled",
                                    isChecked
                            )
                            .apply();

                    Toast.makeText(
                            SettingsActivity.this,
                            isChecked
                                    ? "Alerts enabled"
                                    : "Alerts disabled",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
    }
}
