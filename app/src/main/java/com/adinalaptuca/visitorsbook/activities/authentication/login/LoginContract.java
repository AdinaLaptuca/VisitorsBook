package com.adinalaptuca.visitorsbook.activities.authentication.login;

import com.adinalaptuca.visitorsbook.custom.MvpContract;

interface LoginContract {
    interface View extends MvpContract.View {
        void goToMainscreen();
        void loginPathFetched(String path);
    }

    interface Presenter extends MvpContract.Presenter<LoginContract.View> {
        boolean isSignedIn();
        void trySignIn(String email, String password);
        void forgotPassword(String email);
        void fetchLoginPath();
    }
}
