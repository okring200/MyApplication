package com.example.minjena.myapplication.presenter;

import android.os.Bundle;

import com.example.minjena.myapplication.model.Chat;
import com.example.minjena.myapplication.model.RecyclerChat;

import java.util.ArrayList;

public class ChatPresenter implements IChatPresenter{

    Bundle arg;

    public ChatPresenter(Bundle arg){this.arg=arg;}

    @Override
    public ArrayList<RecyclerChat> getChatList() {
        Chat mChat = new Chat(arg);
        return mChat.getChatList();
    }

    public void sendChat(String chat)
    {

    }
}
