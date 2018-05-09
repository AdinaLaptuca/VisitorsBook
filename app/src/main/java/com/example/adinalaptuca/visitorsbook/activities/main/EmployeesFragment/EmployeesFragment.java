package com.example.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adinalaptuca.visitorsbook.R;

public class EmployeesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.component_visits_take_photo, container, false);
        return view;
    }
}
