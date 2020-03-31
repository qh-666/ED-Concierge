package com.example.edconcierge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
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
    private MessageAdapter mAdapter;
    Switch aSwitch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_message, container, false);
        ((TextView) root.findViewById(R.id.welcome_message)).setText("Hello, " + DataContainer.name);
        aSwitch=root.findViewById(R.id.message_clearnotification);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("Message", String.valueOf(DataContainer.messages.size()));
                    DataContainer.messages=new ArrayList<String>();
                    mAdapter.messageList.clear();
                    Log.d("Message", String.valueOf(DataContainer.messages.size()));
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        setListView();
        return root;
    }

    private void setListView() {
        mAdapter = new MessageAdapter(getContext(), R.layout.message_item, MessageContainer.list);
        ListView listView = (ListView) root.findViewById(R.id.messages_listView);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getActivity());
        broadcaster.registerReceiver((receiver),
                new IntentFilter("hospitalMessage")
        );
        MessageContainer.getMessage(DataContainer.hospitalName,DataContainer.id,broadcaster);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
            Log.d("BroadcastReceiver", "onReceive");
        }
    };

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onDestroy();
    }

}
