package com.example.adinalaptuca.visitorsbook.activities.authentication.signup;

public class Presenter implements SignupContract.Presenter {

    private SignupContract.View view;

    Presenter(SignupContract.View view) {
        this.view = view;
    }

    @Override
    public void trySignUp(String email, String password) {

    }
}
