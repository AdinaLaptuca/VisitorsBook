package com.adinalaptuca.visitorsbook.activities.authentication.signup;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.R;

public class Presenter implements SignupContract.Presenter {

    private SignupContract.View view;

    Presenter(SignupContract.View view) {
        this.view = view;
    }

    @Override
    public void trySignUp(String email, String password) {
        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()
//                .setLanguageCode()
                .createUserWithEmailAndPassword(email, password)
//                .addOnFailureListener(this, e -> {
//
//                    Log.e("singup", "failed: " + e.getMessage());
//                    dismissLoadingDialog();
//                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.registration_failed) + e.getMessage(),
//                            Toast.LENGTH_SHORT).show();
//                })
                .addOnCompleteListener(task -> {
                    view.dismissLoadingDialog();

                    if (!task.isSuccessful()) {
                        view.showToast(view.getContext().getResources().getString(R.string.registration_failed) +
                                (task.getException() != null ? task.getException().getMessage() : ""));
                    } else {
                        view.signedUpSuccesfull();
                    }
                });
    }
}
