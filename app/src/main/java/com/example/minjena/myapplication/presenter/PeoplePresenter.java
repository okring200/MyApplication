package com.example.minjena.myapplication.presenter;

import android.os.Bundle;

import com.example.minjena.myapplication.model.People;

import java.util.ArrayList;

public class PeoplePresenter implements  IPeoplePresenter {

    Bundle arg;

    public PeoplePresenter(Bundle arg)
    {
        this.arg = arg;
    }

    @Override
    public ArrayList<String> getPeopleList() {
        People mPeople = new People(arg);
        return mPeople.getPeopleList();
    }
}
