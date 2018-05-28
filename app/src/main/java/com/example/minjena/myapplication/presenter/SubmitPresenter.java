package com.example.minjena.myapplication.presenter;

import android.text.TextUtils;

import com.example.minjena.myapplication.model.Submit;
import com.example.minjena.myapplication.view.ISubmitView;

import java.util.ArrayList;

public class SubmitPresenter implements ISubmitPresenter {
    ISubmitView iSubmitView;
    Submit mSubmit;
    public SubmitPresenter(ISubmitView iSubmitView)
    {
        mSubmit = new Submit(SubmitPresenter.this);
        this.iSubmitView = iSubmitView;
    }
    @Override
    public void doSubmit(ArrayList list) {
        mSubmit.doSubmit(list);
    }

    @Override
    public void validation(ArrayList<String> list) {
        boolean check = true;
        for(String li:list)
        {
            if(TextUtils.isEmpty(li))
            {
                check = false;
                break;
            }
        }
        if(check == false)
        {
            iSubmitView.validFailed();
            return;
        }/*
        check = mSubmit.validation(list);
        if(check == false)
        {
            iSubmitView.validFailed();
            return;
        }*/
        doSubmit(list);
    }

    @Override
    public void isSuccesss(String success) {
        if(success.equals("1"))
            iSubmitView.submitSuccess();
        else
            iSubmitView.submitError();
    }
}
