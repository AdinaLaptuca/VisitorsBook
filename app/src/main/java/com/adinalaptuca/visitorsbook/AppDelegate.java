package com.adinalaptuca.visitorsbook;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.google.firebase.auth.FirebaseAuth;
import com.microblink.MicroblinkSDK;
import com.microblink.intent.IntentDataTransferMode;

public class AppDelegate extends Application {

    private FirebaseAuth mAuth;
    private String loginPath;

    public static AppDelegate getInstance(Context context) {
        return (AppDelegate) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAuth = FirebaseAuth.getInstance();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // obtain your licence at http://microblink.com/login or contact us at http://help.microblink.com
//        MicroblinkSDK.setLicenseFile("MB_com.adinalaptuca.visitorsbook_BlinkID_Android.mblic", this);         // TODO put a different license for each package

        // use optimised way for transferring RecognizerBundle between activities, while ensuring
        // data does not get lost when Android restarts the scanning activity
        MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

    public String getLoginPath() {
        return loginPath;
    }

    public void setLoginPath(String loginPath) {
        this.loginPath = loginPath;
    }
}
