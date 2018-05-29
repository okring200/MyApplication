package com.example.minjena.myapplication.presenter;

import com.example.minjena.myapplication.model.RecyclerChat;

import java.util.ArrayList;

public interface IChatPresenter {
    ArrayList<RecyclerChat> getChatList();
    void sendChat(String chat);
}
