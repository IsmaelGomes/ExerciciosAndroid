package com.example.ismaelgomes.exercicioviews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsOneActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_one);
        setViews();
    }

    private void setViews(){
        Typeface bold = getSRTpeface(1);
        Typeface light = getSRTpeface(2);

        TextView activityTitle = (TextView)findViewById(R.id.activityTitle);
        activityTitle.setTypeface(bold);

        TextView myName = (TextView)findViewById(R.id.name);
        myName.setTypeface(bold);

        TextView profileName = (TextView)findViewById(R.id.profileName);
        profileName.setTypeface(light);

        Button ds = (Button)findViewById(R.id.dsButton);
        ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsOneActivity.this, SettingsTwo.class);
                intent.putExtra("Settings", getSettingsFromViews());
                startActivity(intent);
            }
        });
    }

    private Settings getSettingsFromViews(){
        Settings s = new Settings();
        s.setAccountActive(((Switch)findViewById(R.id.accountActive)).isChecked());
        s.setPrivateAccount(((CheckBox)findViewById(R.id.privateAccount)).isChecked());
        s.setSubjectField(((CheckBox)findViewById(R.id.subjectField)).isChecked());
        return s;
    }

    private Typeface getSRTpeface(int type){

        Typeface fontSR = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        switch (type){
            case 1: fontSR = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
                break;
            case 2: fontSR = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
                break;
        }
        return fontSR;
    }
}
