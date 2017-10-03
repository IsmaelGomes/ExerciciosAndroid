package com.example.ismaelgomes.exercicioviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsTwo extends AppCompatActivity {

    Settings s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_two);
        Intent it = getIntent();
        s = (Settings) it.getSerializableExtra("Settings");

        final TextView brightnessIndicator = (TextView)findViewById(R.id.dpBrightness);
        SeekBar seek = (SeekBar)findViewById(R.id.brightnessBar);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
               brightnessIndicator.setText(progress + "%");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


    }
}
