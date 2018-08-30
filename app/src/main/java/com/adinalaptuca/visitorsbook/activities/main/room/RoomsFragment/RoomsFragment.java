package com.adinalaptuca.visitorsbook.activities.main.room.RoomsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseFragment;
import com.adinalaptuca.visitorsbook.custom.CustomRecyclerView;
import com.adinalaptuca.visitorsbook.custom.OnItemSelectListener;
import com.adinalaptuca.visitorsbook.model.Room;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class RoomsFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String EXTRA_ROOMS = "EXTRA_ROOMS";

    public static RoomsFragment newInstance(ArrayList<Room> rooms) {
        RoomsFragment fragment = new RoomsFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_ROOMS, rooms);
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.tblData)
    protected CustomRecyclerView tblData;

    private List<Room> listRooms = new ArrayList<>();
    private RoomsAdapter adapter;

    private OnItemSelectListener<Room> mOnItemSelectListener;

    @Override
    protected int layoutId() {
        return R.layout.fragment_rooms;
    }

    @Override
    protected void initView(View v) {
        tblData.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        tblData.setItemClickListener(this);
        adapter = new RoomsAdapter(listRooms);
        tblData.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listRooms.clear();
        listRooms.addAll(getArguments().getParcelableArrayList(EXTRA_ROOMS));
        adapter.notifyDataSetChanged();
    }

    public void setOnItemSelectListener(OnItemSelectListener<Room> listener) {
        this.mOnItemSelectListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mOnItemSelectListener.onItemSelected(this, listRooms.get(i));
        getActivity().onBackPressed();
    }
}
