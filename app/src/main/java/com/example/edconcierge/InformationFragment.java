package com.example.edconcierge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class InformationFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private int mIndexHospital = -1;
    private View root;
    private String mHospitalName;
    private TextView textView;
    private SearchView searchView;
    RecyclerView searchRecyclerView,recyclerView;
    SearchViewAdapter adapter1;

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
        setSearchView();
        setRecyclview();
        return root;
    }

    private void updateQuestions() {
        recyclerView = root.findViewById(R.id.recyclerview_information);
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


    private void setSearchView() {
        searchView=root.findViewById(R.id.info_searchView);
        textView=root.findViewById(R.id.patient_information);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                searchRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                textView.setVisibility(View.VISIBLE);
                searchRecyclerView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter1.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setRecyclview() {
        searchRecyclerView=root.findViewById(R.id.recyclerview_searchinfo);
        searchRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        adapter1=new SearchViewAdapter(getContext(),DataContainer.questionsInformation);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setAdapter(adapter1);
        searchRecyclerView.setVisibility(View.INVISIBLE);
    }
}
