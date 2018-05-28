package com.example.minjena.myapplication.presenter;

import java.util.ArrayList;

public interface ISubmitPresenter {
    void doSubmit(ArrayList list);
    void validation(ArrayList<String> list);
    void isSuccesss(String success);
}
