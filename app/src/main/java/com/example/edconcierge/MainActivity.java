package com.example.edconcierge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private int mIndexHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateIndexHospital();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        SharedViewModel model = ViewModelProviders.of(this).get(SharedViewModel.class);
        model.indexHospital(mIndexHospital);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_navigation, R.id.navigation_information)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d("a","changed");
            }
        });
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void updateIndexHospital() {
        Intent intent = getIntent();
        mIndexHospital = intent.getIntExtra("indexHospital", 0);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","Stop");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("MainActivity","Resume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MainActivity","Pause");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("MainActivity","Destroy");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("MainActivity","Restart");
    }
}
