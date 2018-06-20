package com.example.minjena.myapplication.presenter;

import com.example.minjena.myapplication.model.RecyclerChat;

import java.util.ArrayList;

public interface IChatPresenter {
    ArrayList<RecyclerChat> getChatList();
    String getMsg(String msg);
    void voiceDownload(String msg, String chat);
}
