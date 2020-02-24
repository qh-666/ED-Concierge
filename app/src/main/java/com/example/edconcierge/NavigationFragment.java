package com.example.edconcierge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.r0adkll.slidr.Slidr;

public class NavigationFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private int mIndexHospital;
    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Log.d("NavigationFragment","Create");
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_navigation, container, false);

        updateHospitalName();
        updateDestinations();


        return root;
    }

    private void updateHospitalName() {
        SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.getIndexHospital().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mIndexHospital = integer;
                String hospitalName = getResources().getStringArray(R.array.hospitals_array)[mIndexHospital];
                final TextView textView = root.findViewById(R.id.name_hospital_navigation);
                textView.setText(hospitalName);
            }
        });
    }

    private void updateDestinations() {
        while (mIndexHospital == -1) ;
        String[] destinations = getResources().getStringArray(R.array.destinations_array);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_navigation);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);


        int[] imgs = {R.drawable.destination_01, R.drawable.destination_02, R.drawable.destination_03,
                R.drawable.destination_04, R.drawable.destination_05, R.drawable.destination_06,
                R.drawable.destination_07, R.drawable.destination_08, R.drawable.destination_09};

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), destinations, imgs);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), NavigationActivity.class);
        intent.putExtra("indexHospital", mIndexHospital);
        intent.putExtra("indexQuestion", position);
        startActivity(intent);
    }
}
