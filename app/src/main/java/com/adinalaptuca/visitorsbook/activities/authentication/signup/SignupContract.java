package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import com.adinalaptuca.visitorsbook.custom.MvpContract;

class SignupContract {
    interface View extends MvpContract.View {
        void signedUpSuccesfull();
    }

    interface Presenter extends MvpContract.Presenter<SignupContract.View> {
        void trySignUp(String email, String password);
    }
}
