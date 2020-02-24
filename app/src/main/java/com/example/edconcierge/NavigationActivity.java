package com.example.edconcierge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

public class NavigationActivity extends AppCompatActivity {

    private int mIndexHospital;
    private int mIndexQuestion;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //装场效果
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Slidr.attach(this);
        back=findViewById(R.id.Navi_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mIndexHospital = getIntent().getIntExtra("indexHospital", 0);
        mIndexQuestion = getIntent().getIntExtra("indexQuestion", 0);

        ((TextView) findViewById(R.id.hospital_name_activity_navigation))
                .setText(getResources().getStringArray(R.array.hospitals_array)[mIndexHospital]);

        ((TextView) findViewById(R.id.question_activity_navigation))
                .setText(getResources().getStringArray(R.array.destinations_array)[mIndexQuestion]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("NavigationActivity","Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("NavigationActivity","Stop");
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
}
