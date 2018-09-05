package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import butterknife.BindView;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData.PreviewVisitorDataFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor.UpcomingVisitorFragment;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.ViewVisit.ViewVisitFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.custom.fabSpeedDial.FabSpeedDial;
import com.adinalaptuca.visitorsbook.model.Visit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class VisitsFragment extends BaseToolbarFragment implements VisitsContract.View, VisitsAdapter.OnVisitActionListener {

    private VisitsContract.Presenter presenter;

    private SearchView searchView;
    private MenuItem searchMenuItem;

    @BindView(R.id.tblData)
    protected RecyclerView tblData;

    private VisitsAdapter adapter;

    private View viewDateFilter;
    private CheckBox chkAfter;
    private CheckBox chkBefore;
    private Calendar calendarBefore = null;
    private Calendar calendarAfter = null;

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
        presenter = new VisitsPresenter(this);

        fab.addOnMenuItemClickListener((miniFab, label, itemId) -> {
            searchMenuItem.collapseActionView();        // close toolbar employees
                if (itemId == R.id.actionAddVisitor)
                    addFragment(UpcomingVisitorFragment.newInstance(false));
                else if (itemId == R.id.actionFastCheckin)
                    addFragment(PreviewVisitorDataFragment.newInstance(true));
            });

        tblData.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblData.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new VisitsAdapter(presenter.getVisits(), this);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        createDateFilter();
        applyDateFilter();
    }

    @Override
    public void onResume() {
        super.onResume();

        setHasOptionsMenu(true);
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
        else if (id == R.id.action_dates) {
//            View viewFilter = LayoutInflater.from(getActivity()).inflate(R.layout.component_date_filter, null);
//
//            PopupWindow window = new PopupWindow(getActivity());
//            window.setContentView(viewFilter);
//            window.showAtLocation(getView(), Gravity.END | Gravity.TOP, 0, toolbarActivityInterface.getToolbar().getMeasuredHeight());
////            window.showAsDropDown(toolbarActivityInterface.ge);
//            window.setOutsideTouchable(true);

            toggleDatesFilter();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDateFilter() {
        ViewGroup parent = (ViewGroup) getView();

        viewDateFilter = LayoutInflater.from(getActivity()).inflate(R.layout.component_date_filter, parent, false);

        viewDateFilter.setOnClickListener(v -> hideMenu(parent));

        chkAfter = viewDateFilter.findViewById(R.id.chkAfter);
        chkBefore = viewDateFilter.findViewById(R.id.chkBefore);

        CompoundButton.OnCheckedChangeListener listener = (compoundButton, isChecked) -> {
            if (!isChecked)
                return;

            Calendar calendarToUse = compoundButton == chkAfter ? calendarAfter : calendarBefore;

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());    //, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            if (calendarToUse != null)
                datePickerDialog.updateDate(calendarToUse.get(Calendar.YEAR), calendarToUse.get(Calendar.MONTH), calendarToUse.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.setOnDateSetListener((datePicker, year, month, day) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                if (compoundButton == chkAfter)
                    calendarAfter = calendar;
                else
                    calendarBefore = calendar;

                DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE, Locale.getDefault());
                String prefixText = getResources().getString(compoundButton == chkAfter ? R.string.after : R.string.before);
                compoundButton.setText(String.format("%s %s", prefixText, formatter.format(calendar.getTime())));
            });

            datePickerDialog.setOnCancelListener(dialogInterface -> {
                compoundButton.setChecked(false);
            });

            datePickerDialog.show();
        };

        chkAfter.setOnCheckedChangeListener(listener);
        chkBefore.setOnCheckedChangeListener(listener);

        viewDateFilter.findViewById(R.id.btnApply).setOnClickListener(v -> {
            toggleDatesFilter();
            applyDateFilter();
        });
    }

    private void toggleDatesFilter() {
        ViewGroup parent = (ViewGroup) getView();

        if (parent.findViewById(R.id.viewPopup) == null)
            showMenu(parent);
        else
            hideMenu(parent);
    }

    private void showMenu(ViewGroup parent) {
        parent.addView(viewDateFilter, parent.indexOfChild(parent.findViewById(R.id.fab)));
    }

    private void hideMenu(ViewGroup parent) {
        parent.removeView(parent.findViewById(R.id.viewPopup));
    }

    private void applyDateFilter() {
        presenter.getData(chkAfter.isChecked() ? calendarAfter.getTime() : null,
                chkBefore.isChecked() ? calendarBefore.getTime() : null);
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void doCheckin(Visit visit) {
        addFragment(PreviewVisitorDataFragment.newInstance(visit));
    }

    @Override
    public void doCheckout(Visit visit) {
        showLoadingDialog(null);
        presenter.checkoutVisit(visit);
    }

    @Override
    public void viewVisit(Visit visit) {
        addFragment(ViewVisitFragment.newInstance(visit));
    }

    @Override
    public void removeVisit(Visit visit) {
        showLoadingDialog(null);
        presenter.removeVisit(visit);
    }
}
