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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.r0adkll.slidr.Slidr;

public class NavigationFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private int mIndexHospital;
    private View root;
    private SearchView searchView;
    RecyclerView searchRecyclerView,recyclerView;
    SearchViewAdapter adapter1;
    private TextView textView;

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

        setSearchView();
        setRecyclview();

        Log.d("NavigationFragment", "onCreateView: ");
        return root;
    }

    private void updateDestinations() {
        recyclerView = root.findViewById(R.id.recyclerview_navigation);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), DataContainer.questionsNavigation, DataContainer.iconsNavigation);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), NavigationActivity.class);
        intent.putExtra("indexQuestion", position);
        startActivity(intent);
    }

    private void setSearchView() {
        searchView=root.findViewById(R.id.navigation_searchView);
        textView=root.findViewById(R.id.where_can_i_find);
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
        searchRecyclerView=root.findViewById(R.id.recyclerview_navigation_search);
        searchRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        adapter1=new SearchViewAdapter(getContext(),DataContainer.questionsNavigation);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setAdapter(adapter1);
        searchRecyclerView.setVisibility(View.INVISIBLE);
    }
}
