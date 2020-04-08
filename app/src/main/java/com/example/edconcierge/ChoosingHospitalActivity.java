package com.example.edconcierge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChoosingHospitalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String mHospitalName;
    private List<String> mHospitalNames;
    private boolean mGuestMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_hospital);

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "norwester.otf");
        ((TextView) findViewById(R.id.ed)).setTypeface(face);

        setSwitchListener();

        copyDataBaseToPhone();
        mHospitalNames = getHospitalNames();
        mHospitalName = mHospitalNames.get(0);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mHospitalNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        mHospitalName = mHospitalNames.get(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }




    private void copyDataBaseToPhone() {
        DataBaseUtil util = new DataBaseUtil(this);
        if (!util.checkDataBase()) {
            try {
                util.copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private List<String> getHospitalNames() {
        List<String> hospitalNames = new ArrayList<>();
        DataBaseHelper dbHelper = new DataBaseHelper(this, "EDConcierge.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Hospital", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                hospitalNames.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return hospitalNames;
    }




    private void setSwitchListener() {
        Switch switch_ = (Switch) findViewById(R.id.switch1);
        final EditText nameEditText = (EditText) findViewById(R.id.patient_name_editText);
        final EditText idEditText = (EditText) findViewById(R.id.patient_id_editText);
        final EditText vericodeEditText = (EditText) findViewById(R.id.patient_vericode);


        switch_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mGuestMode = isChecked;
                if (mGuestMode) {
                    nameEditText.setVisibility(View.GONE);
                    idEditText.setVisibility(View.GONE);
                    vericodeEditText.setVisibility(View.GONE);
                } else {
                    nameEditText.setVisibility(View.VISIBLE);
                    idEditText.setVisibility(View.VISIBLE);
                    vericodeEditText.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void nextHandler(View view) {

        if (mGuestMode) {
            nextHandlerHelper(true, null, null);
        } else {
            final String name = ((EditText) findViewById(R.id.patient_name_editText)).getText().toString();
            final String id = ((EditText) findViewById(R.id.patient_id_editText)).getText().toString();
            final String vericode = ((EditText) findViewById(R.id.patient_vericode)).getText().toString();

            if (id.length() == 0 || !id.matches("[0-9]+")) {
                Toast.makeText(this, "Wrong ID format", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").whereEqualTo("id", id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() == 0) {
                                    Toast.makeText(ChoosingHospitalActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                    if (document.get("name").equals(name) && document.get("hospital").equals(mHospitalName) && document.get("vericode").equals(vericode)) {
                                        Log.d("TAG", "onComplete: 09");

                                        // Success
                                        nextHandlerHelper(false, name, id);
                                    } else {
                                        Toast.makeText(ChoosingHospitalActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                                Toast.makeText(ChoosingHospitalActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void nextHandlerHelper(boolean isGuestMode, String username, String id) {
        SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("hospitalName", mHospitalName);
        if (!isGuestMode) {
            FirebaseMessaging.getInstance().subscribeToTopic(id);
            editor.putString("name", username);
            editor.putString("id", id);
        } else {
            editor.putString("name", "guest");
            editor.putString("id", "00000000");
        }
        editor.putString("messages", new Gson().toJson(new ArrayList<>()));
        editor.commit();
        DataContainer.getData(getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE), this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
