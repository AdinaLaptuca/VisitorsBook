package com.adinalaptuca.visitorsbook.activities.authentication.login;

import android.app.Activity;
import android.util.Log;
import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseUser;

class Presenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public Presenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public boolean isSignedIn() {
        return AppDelegate.getInstance(view.getContext()).getFirebaseAuth().getCurrentUser() != null;
    }

    @Override
    public void trySignIn(String email, String password) {
//        RxFirebaseAuth.signInWithEmailAndPassword(AppDelegate.getInstance(view.getContext()).getFirebaseAuth(), email.trim(), password)
//                .asObservable()
//                .take(1)
////                .flatMap(x -> RxFirebaseUser.getToken(FirebaseAuth.getInstance().getCurrentUser(), false))
//                .subscribe(task -> {
//                    view.dismissLoadingDialog();
//
//                    if (task.getUser() != null) {
//                        view.goToMainscreen();
//                    }
//                    else
//                        view.showToast(view.getContext().getResources().getString(R.string.authentication_failed));
//                });

        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()
                .signInWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener((Activity) view.getContext(), task -> {
                    view.dismissLoadingDialog();

                    if (task.isSuccessful()) {
                        view.goToMainscreen();
                    }
                    else
                        view.showToast(view.getContext().getResources().getString(R.string.authentication_failed));
                })
                .addOnFailureListener((Activity) view.getContext(), e -> {
                    view.dismissLoadingDialog();
                    view.showToast(view.getContext().getResources().getString(R.string.authentication_failed) + " ." + e.getMessage());
                    Log.e("login", "failed, e: " + e.getMessage());
                });
    }

    @Override
    public void forgotPassword(String email) {
        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener((Activity) view.getContext(), task -> {
                    Log.e("auth presenter", "login: " +task.isSuccessful());
                });
    }
}
