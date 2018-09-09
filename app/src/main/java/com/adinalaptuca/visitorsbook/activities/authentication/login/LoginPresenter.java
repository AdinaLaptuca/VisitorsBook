package com.adinalaptuca.visitorsbook.activities.authentication.login;

import android.app.Activity;
import android.util.Log;
import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseUser;

class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public boolean isSignedIn() {
        return AppDelegate.getInstance(view.getContext()).getFirebaseAuth().getCurrentUser() != null;
    }

    @Override
    public void trySignIn(String email, String password) {
        AppDelegate.getInstance(view.getContext()).getFirebaseAuth()
                .signInWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener((Activity) view.getContext(), task -> {
                    view.dismissLoadingDialog();

                    if (task.isSuccessful()) {
                        fetchLoginPath();
                    }
                    else
                        view.showToast(view.getContext().getResources().getString(R.string.authentication_failed));
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

    @Override
    public void fetchLoginPath() {
        FirebaseFirestore.getInstance().collection("loginPaths")
                .whereEqualTo("userID", AppDelegate.getInstance(view.getContext()).getFirebaseAuth().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener((Activity) view.getContext(), task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            view.loginPathFetched(document.getString("path"));
                            break;
                        }
                    } else {
                        Log.e("login presenter", "Error getting documents: ", task.getException());
                    }
                });
    }
}
