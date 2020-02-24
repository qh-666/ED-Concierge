package com.example.edconcierge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

public class InformationActivity extends AppCompatActivity {

    private int mIndexHospital;
    private int mIndexQuestion;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        //装场效果
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Slidr.attach(this);

        back=findViewById(R.id.Info_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        Log.d("InfoActivity","Start");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    public void onBackPressed(){
        finish();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("InfoActivity","Destroy");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("InfoActivity","Resume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("InfoActivity","Pause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("InfoActivity","Stop");
    }
}
