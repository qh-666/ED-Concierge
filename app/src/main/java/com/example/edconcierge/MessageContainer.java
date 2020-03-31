package com.example.edconcierge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MessageContainer {

    private static final String TAG = "MessageContainer";

    public static List<Message> list = new ArrayList<>();

    public static void getMessage(String hospitalName, String id, final LocalBroadcastManager broadcaster) {
        // if guest return
        if (id.equals("00000000")) return;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hospital").child(hospitalName).child("user").child(id);

        myRef.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                ArrayList<HashMap<String, String>> messages = (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();
                for (HashMap<String, String> message : messages) {
                    Message msg = new Message(message.get("content"), message.get("time"), message.get("receiver").equals("user"), message.get("type").equals(("text")));
                    list.add(msg);
                }
                for (Message message : list){
                    Log.d(TAG, "onDataChange: " + message);
                    DataContainer.messages.add(message.content);
                    System.out.println("Here2");
                }
                Intent intent = new Intent("hospitalMessage");
                broadcaster.sendBroadcast(intent);
                // TODO: notify view to refresh
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void sendMessage(String hospitalName, String id, String content, boolean isText) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hospital").child(hospitalName).child("user").child(id);

        int numMessage = list.size();
        myRef.child("numMessage").setValue(numMessage + 1);

        HashMap<String, String> message = new HashMap<>();
        message.put("content", content);
        message.put("receiver", "hospital");
        message.put("time", new SimpleDateFormat("MM/dd/yyyy, hh:mm:ss a").
                format(new Date()).
                replaceAll("^0", "").
                replaceAll(" 0", "").
                replaceAll("\\.", "").toUpperCase());
        message.put("type", isText ? "text" : "picture");
        myRef.child("message").child(String.valueOf(numMessage)).setValue(message);
    }
}