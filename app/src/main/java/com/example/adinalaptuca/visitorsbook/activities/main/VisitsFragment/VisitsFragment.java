package com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.adinalaptuca.visitorsbook.R;
import com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment.TakePhoto.TakePhotoFragment;
import com.example.adinalaptuca.visitorsbook.custom.BaseFragment;
import com.example.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

import butterknife.BindView;
import butterknife.OnClick;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
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

        SwipeController swipeController = new SwipeController();

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(tblData);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.getData();
    }

    @OnClick(R.id.fab)
    public void addClicked() {
        addFragment(new TakePhotoFragment());
    }


    class SwipeController extends Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.LEFT );
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
}
