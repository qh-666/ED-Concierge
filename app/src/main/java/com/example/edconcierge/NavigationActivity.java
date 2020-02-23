package com.example.edconcierge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NavigationActivity extends AppCompatActivity {

    private int mIndexHospital;
    private int mIndexQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mIndexHospital = getIntent().getIntExtra("indexHospital", 0);
        mIndexQuestion = getIntent().getIntExtra("indexQuestion", 0);

        ((TextView) findViewById(R.id.hospital_name_activity_navigation))
                .setText(getResources().getStringArray(R.array.hospitals_array)[mIndexHospital]);

        ((TextView) findViewById(R.id.question_activity_navigation))
                .setText(getResources().getStringArray(R.array.destinations_array)[mIndexQuestion]);
    }
}
