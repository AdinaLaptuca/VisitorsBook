package com.adinalaptuca.visitorsbook.custom;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import com.adinalaptuca.visitorsbook.R;
import butterknife.Unbinder;

public class BaseActivity extends Activity implements BaseActivityInterface, MvpContract.View {
    protected Unbinder unbinder;

    private ProgressDialog progressBar;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoadingDialog(String message) {
        if (progressBar == null)
            progressBar = new ProgressDialog(this);

        progressBar.setMessage(message);
        progressBar.show();
    }

    @Override
    public void dismissLoadingDialog() {
        progressBar.dismiss();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void replaceFragment(Fragment fragment) {

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void addFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
//                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        unbinder.unbind();
    }
}
