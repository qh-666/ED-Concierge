package com.example.edconcierge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class InformationActivity extends AppCompatActivity {

    private int mIndexHospital;
    private int mIndexQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        mIndexHospital = getIntent().getIntExtra("indexHospital", 0);
        mIndexQuestion = getIntent().getIntExtra("indexQuestion", 0);

        ((TextView) findViewById(R.id.hospital_name_activity_information))
                .setText(getResources().getStringArray(R.array.hospitals_array)[mIndexHospital]);

        ((TextView) findViewById(R.id.question_activity_information))
                .setText(getResources().getStringArray(R.array.questions_array)[mIndexQuestion]);

        ((TextView) findViewById(R.id.answer_activity_information))
                .setText(getResources().getStringArray(R.array.answers_array)[mIndexQuestion]);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("InformationActivity","Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("InformationActivity","Stop");
    }
}
