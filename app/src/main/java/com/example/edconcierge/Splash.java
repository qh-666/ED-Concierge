package com.example.edconcierge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "norwester.otf");
        ((TextView) findViewById(R.id.ed)).setTypeface(face);
        Log.d("SplashActivity","SetFace");


        DataContainer.clear();

        if (getIntent().getExtras() != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE);
            ArrayList<String> messages = (new Gson()).fromJson(sharedPreferences.getString("messages", ""), ArrayList.class);
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("TAG", "Key: " + key + " Value: " + value);
                if (key.equals("hospitalMessage")) {
                    messages.add((String) value);
                }
            }
            Log.d("SplashActivity", "onCreate: " + messages);
            sharedPreferences.edit().putString("messages", new Gson().toJson(messages)).commit();
        }

        final SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE);
        String hospitalName = sharedPreferences.getString("hospitalName", "");
        if (hospitalName.length() == 0) {
            startChoosingHospitalActivity();
        }
        else {
            getWindow().getDecorView().post(new Runnable() {

                @Override
                public void run() {
                    startMainActivity(sharedPreferences);
                }
            });
        }
    }

    private void startChoosingHospitalActivity() {
        Intent intent = new Intent(this, ChoosingHospitalActivity.class);
        startActivity(intent);
        finish();
    }

    private void startMainActivity(SharedPreferences sharedPreferences) {
        if (sharedPreferences.getString("hospitalName", "").length() != 0) {
            DataContainer.getData(sharedPreferences, this);
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
