package com.example.edconcierge;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.r0adkll.slidr.Slidr;

public class InformationActivity extends AppCompatActivity {

    ImageButton back;
    private int mIndexQuestion;

    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        //装场效果
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Slidr.attach(this);

        back = findViewById(R.id.Info_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mIndexQuestion = getIntent().getIntExtra("indexQuestion", 0);


        ((TextView) findViewById(R.id.hospital_name_activity_information))
                .setText(DataContainer.hospitalName);

        ((TextView) findViewById(R.id.question_activity_information))
                .setText(DataContainer.questionsInformation.get(mIndexQuestion));

        ((TextView) findViewById(R.id.answer_activity_information))
                .setText(DataContainer.answersInformation.get(mIndexQuestion));


        if (mTextToSpeech == null) {
            mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {

                    } else {

                    }
                }
            });
        }


        Log.d("TAG", "onCreate: " + ((TextView) findViewById(R.id.answer_activity_information)).getText());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("InfoActivity", "Start");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if(mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
        Log.d("InfoActivity", "Destroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("InfoActivity", "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("InfoActivity", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("InfoActivity", "Stop");
    }

    public void textToSpeech(View view) {
        mTextToSpeech.speak(DataContainer.answersInformation.get(mIndexQuestion), TextToSpeech.QUEUE_ADD, null);
    }

    public void increaseFontSize(View view) {
        TextView textView = findViewById(R.id.answer_activity_information);
        if (textView.getTextSize() < 95.0)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + 10);
    }

    public void decreaseFontSize(View view) {
        TextView textView = findViewById(R.id.answer_activity_information);
        if (textView.getTextSize() > 45.0)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() - 10);
    }


}
