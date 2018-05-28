package com.example.minjena.myapplication.presenter;

import android.os.Bundle;

import java.util.ArrayList;

public class ChatPresenter implements IChatPresenter{

    Bundle arg;

    public ChatPresenter(Bundle arg){this.arg=arg;}

    @Override
    public ArrayList<String> getChatList() {
        return null;
    }
}
