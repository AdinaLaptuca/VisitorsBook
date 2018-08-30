package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor.UpcomingVisitorFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.custom.fabSpeedDial.FabSpeedDial;
import com.adinalaptuca.visitorsbook.model.Checkin;
import com.adinalaptuca.visitorsbook.model.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
            searchMenuItem.collapseActionView();        // close toolbar employees
                if (itemId == R.id.actionAddVisitor)
                    addFragment(UpcomingVisitorFragment.newInstance(false));
                else if (itemId == R.id.actionFastCheckin)
                    addFragment(PreviewVisitorDataFragment.newInstance(true));
            });

        tblData.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new VisitsAdapter(presenter.getVisits());
        tblData.setAdapter(adapter);

//        String roomsPath = String.format(Locale.getDefault(), "%s/%s",
//                AppDelegate.getInstance(getActivity()).getLoginPath(),
//                "rooms");
//
//        List<Room> list = Arrays.asList(Room.builder().setName("Selena").setFloor(5).setNoSpots(4).build(),
//                Room.builder().setName("Moonwalk").setFloor(-1).setNoSpots(5).setUtilities(Arrays.asList(Room.FLIPCHART)).build(),
//                Room.builder().setName("Beatles").setFloor(7).setNoSpots(4).setUtilities(Arrays.asList(Room.CONFERENCE_PHONE, Room.FLIPCHART)).build(),
//                Room.builder().setName("Materhorn").setFloor(6).setNoSpots(20).setUtilities(Arrays.asList(Room.CONFERENCE_PHONE, Room.SMART_FLIPCHART, Room.VIDEO_PROJECTOR)).build(),
//                Room.builder().setName("Arsenal").setFloor(5).setNoSpots(4).setUtilities(Arrays.asList(Room.FLIPCHART, Room.WHITEBOARD)).build(),
//                Room.builder().setName("Invincibles").setFloor(1).setNoSpots(11).setUtilities(Arrays.asList(Room.WHITEBOARD)).build(),
//                Room.builder().setName("Hebe").setFloor(0).setNoSpots(6).setUtilities(Arrays.asList(Room.FLIPCHART, Room.WHITEBOARD)).build(),
//                Room.builder().setName("Success").setFloor(-1).setNoSpots(10).setUtilities(Arrays.asList(Room.CONFERENCE_PHONE)).build(),
//                Room.builder().setName("Victory").setFloor(5).setNoSpots(3).setUtilities(Arrays.asList(Room.FLIPCHART, Room.WHITEBOARD)).build());
//
//        for (Room room : list) {
//            FirebaseFirestore.getInstance().collection(roomsPath)
//                    .add(room);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();

        setHasOptionsMenu(true);

//        setMenuVisibility(true);
//        getActivity().invalidateOptionsMenu();

//        toolbarActivityInterface.getToolbar().inflateMenu(R.menu.main_screen);
//
//        Menu menu = toolbarActivityInterface.getToolbar().getMenu();
//
//        searchMenuItem = menu.findItem(R.id.action_search);
//
//        searchView = (SearchView) searchMenuItem.getActionView();
//        searchView.setQueryHint(getResources().getString(R.string.search));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Log.e("visit", "onQueryTextChange");
////                presenter.setSearchString(newText);
//                return true;
//            }
//        });
//
//        toolbarActivityInterface.getToolbar().showOverflowMenu();
    }

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
