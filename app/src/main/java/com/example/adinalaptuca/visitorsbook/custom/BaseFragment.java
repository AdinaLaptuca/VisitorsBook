package com.example.adinalaptuca.visitorsbook.custom;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adinalaptuca.visitorsbook.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements MvpContract.View {
    protected Unbinder unbinder;
    protected MvpContract.View baseActivity;

    @Override
    public void showLoadingDialog(String message) {
        baseActivity.showLoadingDialog(message);
    }

    @Override
    public void dismissLoadingDialog() {
        baseActivity.dismissLoadingDialog();
    }

    @Override
    public void showToast(String message) {
        baseActivity.showToast(message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MvpContract.View)
            baseActivity = (MvpContract.View) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    protected abstract int layoutId();

    protected void initView() {}

    protected void addFragment(Fragment fragment) {
        getChildFragmentManager()
//        getActivity().getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_from_up, R.animator.exit_to_down, R.animator.enter_from_down, R.animator.exit_to_up)
                .add(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (unbinder != null)
            unbinder.unbind();
    }
}
