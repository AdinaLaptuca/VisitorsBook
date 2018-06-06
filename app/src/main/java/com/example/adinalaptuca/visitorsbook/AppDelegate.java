package com.example.adinalaptuca.visitorsbook;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class AppDelegate extends Application {

    private FirebaseAuth mAuth;

    public static AppDelegate getInstance(Context context) {
        return (AppDelegate) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }
}
