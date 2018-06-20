package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import butterknife.BindView;
import butterknife.OnClick;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

public class VisitsFragment extends BaseToolbarFragment implements VisitsContract.View {

    private VisitsContract.Presenter presenter;

    @BindView(R.id.tblData)
    protected RecyclerView tblData;

    private VisitsAdapter adapter;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.visits);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_visits;
    }

    protected void initView() {
        presenter = new Presenter(this);

        tblData.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new VisitsAdapter(presenter.getVisits());
        tblData.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.getData();
    }

    @OnClick(R.id.fab)
    public void addClicked() {
        addFragment(new PreviewVisitorDataFragment());
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
}
