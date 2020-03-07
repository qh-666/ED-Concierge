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

        Log.d("NavigationFragment", "onCreate: ");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_navigation, container, false);
        updateDestinations();
        Log.d("NavigationFragment", "onCreateView: ");
        return root;
    }

    private void updateDestinations() {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_navigation);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        Log.d("RecyclerView","3");
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), DataContainer.questionsNavigation, DataContainer.iconsNavigation);
        adapter.setClickListener(this);
        Log.d("RecyclerView","4");
        recyclerView.setAdapter(adapter);
        Log.d("RecyclerView","5");
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), NavigationActivity.class);
        intent.putExtra("indexQuestion", position);
        startActivity(intent);
    }
}
