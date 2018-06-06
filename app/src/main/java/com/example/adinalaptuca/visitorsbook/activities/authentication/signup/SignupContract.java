package com.example.adinalaptuca.visitorsbook.activities.authentication.signup;

import com.example.adinalaptuca.visitorsbook.custom.MvpContract;

public class SignupContract {
    interface View extends MvpContract.View {

    }

    interface Presenter extends MvpContract.Presenter<SignupContract.View> {
        void trySignUp(String email, String password);
    }
}
