package com.example.edconcierge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChoosingHospitalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int mIndexHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_hospital);

        Typeface face;
        face = Typeface.createFromAsset(getAssets(), "norwester.otf");
        ((TextView) findViewById(R.id.ed)).setTypeface(face);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hospitals_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        mIndexHospital = pos;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    public void nextHandler(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("indexHospital", mIndexHospital);
        startActivity(intent);
        finish();
    }
}
