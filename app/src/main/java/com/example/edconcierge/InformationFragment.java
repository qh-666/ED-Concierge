package com.example.edconcierge;

import android.content.Intent;
import android.os.Bundle;
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

public class InformationFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private int mIndexHospital = -1;
    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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


        root = inflater.inflate(R.layout.fragment_information, container, false);

        updateHospitalName();

        updateQuestions();


        return root;
    }

    private void updateHospitalName() {
        SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.getIndexHospital().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mIndexHospital = integer;
                String hospitalName = getResources().getStringArray(R.array.hospitals_array)[mIndexHospital];
                final TextView textView = root.findViewById(R.id.name_hospital_information);
                textView.setText(hospitalName);
            }
        });
    }

    private void updateQuestions() {
        while (mIndexHospital == -1) ;
        String[] questions = getResources().getStringArray(R.array.questions_array);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_information);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);


        int[] imgs = {R.drawable.question_01, R.drawable.question_02, R.drawable.question_03,
                R.drawable.question_04, R.drawable.question_05, R.drawable.question_06,
                R.drawable.question_07, R.drawable.question_08};

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(getContext(), questions, imgs);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), InformationActivity.class);
        intent.putExtra("indexHospital", mIndexHospital);
        intent.putExtra("indexQuestion", position);
        startActivity(intent);
    }
}
