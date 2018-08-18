package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import butterknife.BindView;

import android.transition.TransitionManager;
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

//    @OnClick(R.id.fab)
//    public void addClicked() {
//        //fab with 2 options: add un announced visitor (add visitor + checkin step), only add visitor
//
//        final CollectionReference ref = FirebaseFirestore.getInstance().collection("tests");
//
//        ref.document("list").get().addOnCompleteListener(task -> {
//            List<String> list = (List<String>) task.getResult().get("list");
//            list.add(Integer.toString(new Random().nextInt(999)));
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("list", list);
////            ref.document("list").update(map);
//            ref.document("list").set(map, SetOptions.merge());
//        });
//
//        ref.document("list").addSnapshotListener(getActivity(), (querySnapshot, e) -> {
//            List<String> list1 = (List<String>) querySnapshot.get("list");
//            querySnapshot.getId();
//            Log.e("asd", "list: " + list1);
//        });
//    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
}
