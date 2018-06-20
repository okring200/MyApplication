package com.example.minjena.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.minjena.myapplication.R;
import com.example.minjena.myapplication.adapter.chatAdapater;
import com.example.minjena.myapplication.model.RecyclerChat;
import com.example.minjena.myapplication.presenter.ChatPresenter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.client.Socket;
import io.socket.client.IO;

public class ChatActivity extends Activity {

    private ChatPresenter mChatPresenter;
    private Bundle arg;
    private String myidx;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RecyclerChat> recyclerChatList;
    private Button btnSend;
    private EditText etChat;
    private Socket socket;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        arg = getIntent().getExtras();
        arg.putString( "room", "1");
        mChatPresenter = new ChatPresenter(arg);

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        btnSend = (Button)findViewById(R.id.btnSend);
        etChat = (EditText)findViewById(R.id.etChat);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set data
        recyclerChatList = mChatPresenter.getChatList();

        // specify an adapter (see also next example)
        myidx = arg.getString("key");
        mAdapter = new chatAdapater(recyclerChatList, myidx, new chatAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerChat temp) {
                //Log.d("asd",temp.getMsg().toString());
                String midx = mChatPresenter.getMsg(temp.getMsg().toString());
                mChatPresenter.voiceDownload(midx,temp.getMsg().toString());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        scrollToBottom();
        try {
            socket = IO.socket("http://13.125.248.74");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
        socket.on("message", handleRevMessage);
        btnSend.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String ridx = arg.getString("rkey");
        String idx = arg.getString("key");
        String msg = etChat.getText().toString().trim();
        if(msg.equals(""))
            return;
        Date date = new Date(System.currentTimeMillis());
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //sdf.format(date);
        RecyclerChat data = new RecyclerChat(ridx,idx,msg);
        etChat.setText("");
        addMessage(data);
        Gson gson = new Gson();
        String message = gson.toJson(data);
        socket.emit("message",message);
    }

    private Emitter.Listener handleRevMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    RecyclerChat temp;
                    try {
                        String ridx = data.getString("ridx");
                        String fidx = data.getString("from_idx");
                        //String d = data.getString("reg_date");
                        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        //java.util.Date to = sdf.parse(d);
                        String msg = data.getString("msg");
                        temp = new RecyclerChat(ridx,fidx,msg);
                        addMessage(temp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void addMessage(RecyclerChat message) {
        recyclerChatList.add(message);
        mAdapter = new chatAdapater(recyclerChatList, myidx, new chatAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerChat temp) {
                Log.d("asd",temp.getMsg());
            }
        });
        mAdapter.notifyItemInserted(0);
        scrollToBottom();
    }

    private void scrollToBottom() {
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

}
