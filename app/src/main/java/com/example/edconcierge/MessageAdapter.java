package com.example.edconcierge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class MessageAdapter extends ArrayAdapter {
    List<Message> messageList;
    public MessageAdapter(Context context, int ResourceID, List<Message> messageList){
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item2, parent, false);
        }
        final Message message=messageList.get(messageList.size()-position-1);
        final String content=message.getContent();
        String time=message.time;
        LinearLayout left=convertView.findViewById(R.id.left_layout);
        LinearLayout right=convertView.findViewById(R.id.right_layout);
        final View replyView=convertView.findViewById(R.id.reply_layout);

        //insvisible还占地方，gone不占据空间
        replyView.setVisibility(View.GONE);

        if(message.toUser){
            left.setVisibility(View.VISIBLE);
            right.setVisibility(View.GONE);
            replyView.setVisibility(View.GONE);
            TextView textView=convertView.findViewById(R.id.left_msg);
            textView.setText(content);
            TextView textView1=convertView.findViewById(R.id.left_msg_time);
            textView1.setText(time);
        }
        else{
            right.setVisibility(View.VISIBLE);
            left.setVisibility(View.GONE);
            replyView.setVisibility(View.GONE);
            TextView textView=convertView.findViewById(R.id.right_msg);
            textView.setText(content);
            TextView textView1=convertView.findViewById(R.id.right_msg_time);
            textView1.setText(time);
        }
        //ScrollView scrollView=convertView.findViewById(R.id.scrollview);
        //TextView textView=convertView.findViewById(R.id.left_msg);
        //TextView textView1=convertView.findViewById(R.id.Message_Time);
        //textView.setText(content);
        //textView1.setText(time);
        if(message.toUser){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),MessageActivity.class);
                    intent.putExtra("index",messageList.size()-position-1);
                    getContext().startActivity(intent);
                }
            });
        }
        final View finalConvertView = convertView;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(message.toUser){
                    //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                    //LayoutInflater myLayout = LayoutInflater.from(getContext());
                    //View dialogView = myLayout.inflate(R.layout.reply_layout, null);
                    //初始化view
                    System.out.println("onlongclick");
                    Message_SendMessageView sendMessageView=new Message_SendMessageView(getContext(), finalConvertView);
                    return true;
                }else return true;
            }
        });
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        Log.d("Message","notifyDataSetChanged()");
        super.notifyDataSetChanged();
    }

}
