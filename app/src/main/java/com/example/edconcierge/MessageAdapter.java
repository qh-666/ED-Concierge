package com.example.edconcierge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.List;

public class MessageAdapter<String> extends ArrayAdapter {
    List<String> messageList;
    public MessageAdapter(Context context, int ResourceID, List<String> messageList){
        super(context,ResourceID,messageList);
        //反转list
        //Collections.reverse(messageList);
        this.messageList=messageList;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    //getView是每一个子项滚动到屏幕内的时候被调用，
    // 首先getitem获得当前项的实例，然后使用layoutinflater来为这个子项加载传入的布局
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(messageList.isEmpty()) return convertView;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        }
        String message=messageList.get(messageList.size()-position-1);
        Log.d("MessageAdapter", java.lang.String.valueOf(position));
        System.out.println(parent);
        TextView textView=convertView.findViewById(R.id.Message_Text);
        textView.setText((CharSequence) message);//需要cast to charsequence
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MessageActivity.class);
                intent.putExtra("index",messageList.size()-position-1);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }


}
