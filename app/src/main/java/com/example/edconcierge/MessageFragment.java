package com.example.edconcierge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private View root;
    private ArrayAdapter<String> mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_message, container, false);
        ((TextView) root.findViewById(R.id.welcome_message)).setText("Hello, " + DataContainer.name);
        setListView();
        return root;
    }

    private void setListView() {
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, DataContainer.messages);
        ListView listView = (ListView) root.findViewById(R.id.messages_listView);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver),
                new IntentFilter("hospitalMessage")
        );

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
            Log.d("BroadcastReceiver", "onReceive: " + DataContainer.messages);
        }
    };

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onDestroy();
    }
}
