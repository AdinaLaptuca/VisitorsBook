package com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adinalaptuca.visitorsbook.R;
import com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment.TakePhoto.TakePhotoFragment;
import com.example.adinalaptuca.visitorsbook.custom.BaseFragment;
import com.example.adinalaptuca.visitorsbook.custom.BaseFragmentInterface;

import butterknife.BindView;
import butterknife.OnClick;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
public class VisitsFragment extends BaseFragment implements VisitsContract.View {

    private VisitsContract.Presenter presenter;

    @BindView(R.id.tblVisits)
    protected RecyclerView tblVisits;

    private VisitsAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.fragment_visits;
    }

    protected void initView() {
        presenter = new Presenter(this);

        tblVisits.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblVisits.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new VisitsAdapter(presenter.getVisits());
        tblVisits.setAdapter(adapter);

        SwipeController swipeController = new SwipeController();

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(tblVisits);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.getData();
    }

    @OnClick(R.id.fab)
    public void addClicked() {
        ((BaseFragmentInterface) getActivity()).replaceFragment(new TakePhotoFragment());
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
