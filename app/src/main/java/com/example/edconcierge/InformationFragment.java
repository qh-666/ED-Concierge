package com.example.edconcierge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InformationFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private int mIndexHospital = -1;
    private View root;
    private String mHospitalName;


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
        updateQuestions();
        return root;
    }

    private void updateQuestions() {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_information);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), DataContainer.questionsInformation, DataContainer.iconsInformation);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        Log.d("TAG", "updateQuestions!: ");
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), InformationActivity.class);
        intent.putExtra("indexQuestion", position);
        startActivity(intent);
    }
}
