package com.example.minjena.myapplication.presenter;

import com.example.minjena.myapplication.model.Login;
import com.example.minjena.myapplication.view.ILoginView;

public class LoginPresenter implements  ILoginPresenter{
    ILoginView iLoginView;
    Login mLogin;
    public LoginPresenter(ILoginView iLoginView) {
        mLogin = new Login(LoginPresenter.this);
        this.iLoginView = iLoginView;
    }

    public void doLogin(String id, String passwd)
    {
        if(id == null || passwd == null)
            iLoginView.loginError();
        else
            mLogin.doLogin(id,passwd);
    }

    @Override
    public void validation(String check) {
        if(check.equals("0"))
            iLoginView.loginError();
        else
            iLoginView.loginSuccess(check);
    }
}
