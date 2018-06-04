package com.example.adinalaptuca.visitorsbook.custom;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adinalaptuca.visitorsbook.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected Unbinder unbinder;

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
