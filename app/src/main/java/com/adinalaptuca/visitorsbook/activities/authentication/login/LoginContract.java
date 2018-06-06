package com.adinalaptuca.visitorsbook.activities.authentication.login;

import com.adinalaptuca.visitorsbook.custom.MvpContract;

interface LoginContract {
    interface View extends MvpContract.View {
        void goToMainscreen();
    }

    interface Presenter extends MvpContract.Presenter<LoginContract.View> {
        boolean isSignedIn();
        void trySignIn(String email, String password);
    }
}
