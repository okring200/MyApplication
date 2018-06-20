package com.example.minjena.myapplication.presenter;

import android.os.Bundle;

import com.example.minjena.myapplication.model.Chat;
import com.example.minjena.myapplication.model.RecyclerChat;
import com.example.minjena.myapplication.model.Voice;

import java.util.ArrayList;

public class ChatPresenter implements IChatPresenter{

    Bundle arg;

    public ChatPresenter(Bundle arg){this.arg=arg;}

    @Override
    public ArrayList<RecyclerChat> getChatList() {
        Chat mChat = new Chat(arg);
        return mChat.getChatList();
    }

    @Override
    public String getMsg(String msg) {
        Chat mChat = new Chat(arg);
        return mChat.getMsg(msg);
    }

    @Override
    public void voiceDownload(String msg, String chat) {
        Voice mVoice = new Voice(arg, msg, chat);
        mVoice.downAndPlay();
    }
}
