package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseFragment;
import butterknife.BindView;

public class EmployeesFragment extends BaseFragment implements EmployeesContract.View {

    private EmployeesContract.Presenter presenter;

    @BindView(R.id.tblData)
    protected RecyclerView tblData;

    private EmployeesAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.fragment_employees;
    }

    @Override
    protected void initView(View v) {
        presenter = new Presenter(this);

        tblData.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new EmployeesAdapter(presenter.getEmployees());
        tblData.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.getData();
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
}
