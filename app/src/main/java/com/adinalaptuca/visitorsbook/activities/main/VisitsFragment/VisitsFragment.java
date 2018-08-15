package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import butterknife.BindView;
import butterknife.OnClick;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.Log;

import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor.UpcomingVisitorFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

//        final CollectionReference ref = FirebaseFirestore.getInstance().collection("tests");
//
//        ref.document("list").get().addOnCompleteListener(task -> {
//            List<String> list = (List<String>) task.getResult().get("list");
//            list.add(Integer.toString(new Random().nextInt(999)));
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("list", list);
//            ref.document("list").set(map, SetOptions.merge());
//        });
//
//        ref.document("list").addSnapshotListener(getActivity(), (querySnapshot, e) -> {
//            List<String> list1 = (List<String>) querySnapshot.get("list");
//            querySnapshot.getId();
//            Log.e("asd", "list: " + list1);
//        });
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
}
