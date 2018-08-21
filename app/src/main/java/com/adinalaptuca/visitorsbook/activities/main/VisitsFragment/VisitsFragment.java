package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import butterknife.BindView;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor.UpcomingVisitorFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.custom.fabSpeedDial.FabSpeedDial;
import com.adinalaptuca.visitorsbook.model.Checkin;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VisitsFragment extends BaseToolbarFragment implements VisitsContract.View {

    private VisitsContract.Presenter presenter;

    private SearchView searchView;
    private MenuItem searchMenuItem;

    @BindView(R.id.tblData)
    protected RecyclerView tblData;

    private VisitsAdapter adapter;

    @BindView(R.id.fab)
    protected FabSpeedDial fab;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.visits);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_visits;
    }

    protected void initView(View view) {
        presenter = new Presenter(this);

//        setHasOptionsMenu(true);

        fab.addOnMenuItemClickListener((miniFab, label, itemId) -> {
            searchMenuItem.collapseActionView();        // close toolbar search
                if (itemId == R.id.actionAddVisitor)
                    addFragment(UpcomingVisitorFragment.newInstance(false));
                else if (itemId == R.id.actionFastCheckin)
                    addFragment(PreviewVisitorDataFragment.newInstance(true));
            });

        tblData.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new VisitsAdapter(presenter.getVisits());
        tblData.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

//        setMenuVisibility(true);
        setHasOptionsMenu(true);
//        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onPause() {
        super.onPause();

//        setHasOptionsMenu(false);
        setMenuVisibility(false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main_screen, menu);
        super.onCreateOptionsMenu(menu, inflater);

        searchMenuItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.setSearchString(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            TransitionManager.beginDelayedTransition((ViewGroup) getActivity().findViewById(R.id.toolbar));
            item.expandActionView();
//            MenuItemCompat.expandActionView(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
