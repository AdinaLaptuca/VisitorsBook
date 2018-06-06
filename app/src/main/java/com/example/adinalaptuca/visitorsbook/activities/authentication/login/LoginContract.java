package com.example.adinalaptuca.visitorsbook.activities.authentication.login;

import android.content.Context;

import com.example.adinalaptuca.visitorsbook.custom.MvpContract;

import java.util.List;

interface LoginContract {
    interface View extends MvpContract.View {
        void goToMainscreen();
    }

    interface Presenter extends MvpContract.Presenter<LoginContract.View> {
        boolean isSignedIn();
        void trySignIn(String email, String password);
    }
}
