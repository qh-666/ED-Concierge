package com.example.edconcierge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.util.HashMap;

public class NavigationActivity extends AppCompatActivity {

    private int mIndexQuestion;
    ImageButton back;
    ImageView map;
    Intent intent;
    HashMap<Integer,int[]> locationMap=new HashMap<>();
    //service binder and connection
    NaviagationService.pathfindBinder mbinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mbinder= (NaviagationService.pathfindBinder) service;
            mbinder.imagetoArray(map);
            mbinder.findpath(map,locationMap.get(9),locationMap.get(mIndexQuestion));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("NavigationService","onServiceDisconnected");
        }
    };

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Log.d("NavigationActivity","onCreate");
        //装场效果
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        Slidr.attach(this);
        initlocation();
        back=findViewById(R.id.Navi_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mIndexQuestion = getIntent().getIntExtra("indexQuestion", 0);
        System.out.println(mIndexQuestion);

        ((TextView) findViewById(R.id.hospital_name_activity_navigation))
                .setText(DataContainer.hospitalName);


        ((TextView) findViewById(R.id.question_activity_navigation))
                .setText(DataContainer.questionsNavigation.get(mIndexQuestion));

        map=findViewById(R.id.map);
        intent=new Intent(this,NaviagationService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    private void initlocation() {
        locationMap.put(0,new int[]{85,14});//water
        locationMap.put(1,new int[]{80,118});//bathroom
        locationMap.put(2,new int[]{80,99});//waitingroom
        locationMap.put(3,new int[]{85,30});//food
        locationMap.put(4,new int[]{18,99});//nurse station
        locationMap.put(5,new int[]{51,122});//clerk
        locationMap.put(6,new int[]{22,30});//xray
        locationMap.put(7,new int[]{89,109});//exit
        locationMap.put(8,new int[]{15,73});//parklot(elevator)
        locationMap.put(9,new int[]{50,132});//current
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("NavigationActivity","Start");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("NavigationActivity","Resume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("NavigationActivity","Stop");
    }
    @Override
    protected void onDestroy() {
        //unbind&stop，和activity一起
        unbindService(connection);
        stopService(intent);
        super.onDestroy();
        Log.d("NavigationActivity","Destroy");
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
