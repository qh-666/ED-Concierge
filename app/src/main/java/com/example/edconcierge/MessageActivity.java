package com.example.edconcierge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

import org.w3c.dom.Text;

public class MessageActivity extends AppCompatActivity {
    int messageindex;
    ImageButton back;
    Button fontsize;
    TextView textView;
    private TextToSpeech mTextToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Log.d("MessageActivity", "Create");
        //装场效果
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Slidr.attach(this);

        textView=findViewById(R.id.answer_activity_Message);
        Intent intent=getIntent();
        messageindex = getIntent().getIntExtra("index", 0);
        textView.setText("abc");
        System.out.println("123");
        textView.setText(DataContainer.messages.get(messageindex));

        back = findViewById(R.id.Message_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TextView) findViewById(R.id.hospital_name_activity_Message))
                .setText(DataContainer.hospitalName);

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
    protected void onResume() {
        super.onResume();
        Log.d("MessageActivity", "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MessageActivity", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MessageActivity", "Stop");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MessageActivity", "Start");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MessageActivity", "ReStart");
    }
    @Override
    protected void onDestroy() {
        if(mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
        Log.d("MessageActivity", "Destroy");
    }

    public void textToSpeech(View view) {
        mTextToSpeech.speak(DataContainer.messages.get(messageindex), TextToSpeech.QUEUE_ADD, null);
    }

    public void increaseFontSize(View view) {
        if (textView.getTextSize() < 95.0)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + 10);
    }

    public void decreaseFontSize(View view) {
        if (textView.getTextSize() > 45.0)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() - 10);
    }

}
