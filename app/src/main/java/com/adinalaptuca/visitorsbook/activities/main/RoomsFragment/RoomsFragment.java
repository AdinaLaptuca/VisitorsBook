package com.adinalaptuca.visitorsbook.activities.main.RoomsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseFragment;
import butterknife.BindView;

public class RoomsFragment extends BaseFragment implements RoomsContract.View{

    private RoomsContract.Presenter presenter;

    @BindView(R.id.tblData)
    protected RecyclerView tblData;

    private RoomsAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.fragment_rooms;
    }

    protected void initView() {
        presenter = new Presenter(this);

        tblData.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new RoomsAdapter(presenter.getRooms());
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
