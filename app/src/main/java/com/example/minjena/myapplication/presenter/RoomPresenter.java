package com.example.minjena.myapplication.presenter;

import android.os.Bundle;

import com.example.minjena.myapplication.model.RecyclerRoom;
import com.example.minjena.myapplication.model.Room;

import java.util.ArrayList;

public class RoomPresenter implements  IRoomPresenter {
    Bundle arg;

    public RoomPresenter(Bundle arg)
    {
        this.arg = arg;
    }

    @Override
    public ArrayList<RecyclerRoom> getRoomList() {
        Room mPeople = new Room(arg);
        return mPeople.getRoomList();
    }
}
