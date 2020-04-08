package com.example.edconcierge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.example.edconcierge.PayAnimatorView.STATUS_FAIL;
import static com.example.edconcierge.PayAnimatorView.STATUS_LOADING;
import static com.example.edconcierge.PayAnimatorView.STATUS_SUCCESS;

//自定义的view是一个类，别的view include自己创建的view之后，需要constructor
public class Message_SendMessageView {
    Message_SendMessageView message_sendMessageView;
    private EditText editText;
    private ImageButton button,cancel;
    private TextView textView2;
    PayAnimatorView myProgressBar;
    View reply_view;
    Handler handler;
    Message_SendMessageView(final Context context, View v){
        editText=v.findViewById(R.id.sendMessage_editText);
        editText.setText(null);

        button=v.findViewById(R.id.sendMessageButtom);
        textView2=v.findViewById(R.id.textView);
        myProgressBar=v.findViewById(R.id.myProgressBar);
        reply_view=v.findViewById(R.id.reply_layout);
        cancel=v.findViewById(R.id.sendMessage_cancelView);

        editText.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        reply_view.setVisibility(View.VISIBLE);
        myProgressBar.setVisibility(View.INVISIBLE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reply_view.setVisibility(View.GONE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String input=editText.getText().toString();

                onSend(v,context);
                handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hasSent();
                    }
                },1200);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //myProgressBar.setVisibility(View.INVISIBLE);
                        reply_view.setVisibility(View.GONE);
                        MessageContainer.sendMessage(DataContainer.hospitalName,DataContainer.id,input,true);
                    }
                },3500);
            }
        });
    }
    private void onSend(View v,Context context){
        editText.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        //取消键盘
        InputMethodManager imm =  (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        myProgressBar.setStatus(STATUS_LOADING);
        myProgressBar.setVisibility(View.VISIBLE);
    }
    public void hasSent(){
        System.out.println("onsuccess");
        myProgressBar.setStatus(STATUS_SUCCESS);
    }
}
