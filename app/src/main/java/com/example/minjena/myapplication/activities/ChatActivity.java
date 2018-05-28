package com.example.minjena.myapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.minjena.myapplication.R;
import com.example.minjena.myapplication.adapter.chatAdapater;
import com.example.minjena.myapplication.presenter.ChatPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.client.Socket;
import io.socket.client.IO;

public class ChatActivity extends Activity{

    ChatPresenter mChatPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btnSend;
    private EditText etChat;
    private Socket socket;
    {
        try{
            socket = IO.socket("http://13.125.248.74");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    ArrayList<String> myDataset;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Bundle arg = getIntent().getExtras();
        //arg.putString( "room", "1");

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
        //mChatPresenter.getChatList();
        myDataset = new ArrayList<String>();
        myDataset.add("안녕");
        myDataset.add("hihi");

        // specify an adapter (see also next example)
        mAdapter = new chatAdapater(myDataset);
        mRecyclerView.setAdapter(mAdapter);

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
        String message = etChat.getText().toString().trim();
        etChat.setText("");
        addMessage(message);
        socket.emit("message",message);
    }

    private Emitter.Listener handleRevMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message = "";
                    try {
                        message = data.getString("message").toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    addMessage(message);
                }
            });
        }
    };

    private void addMessage(String message) {
        myDataset.add(message);
        mAdapter = new chatAdapater(myDataset);
        mAdapter.notifyItemInserted(0);
        scrollToBottom();
    }

    private void scrollToBottom() {
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }
}
