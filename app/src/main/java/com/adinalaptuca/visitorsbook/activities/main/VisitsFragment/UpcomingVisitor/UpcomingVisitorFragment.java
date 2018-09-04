package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment.EmployeesFragment;
import com.adinalaptuca.visitorsbook.activities.main.room.RoomsFilter.RoomsFilterFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Employee;
import com.adinalaptuca.visitorsbook.model.Room;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class UpcomingVisitorFragment extends BaseToolbarFragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DialogInterface.OnDismissListener,
        UpcomingVisitorContract.View {

    public interface UpcomingVisitorListener {
        void onFullVisitCreated();
    }

    private static final String EXTRA_FULL_VISIT            = "EXTRA_FULL_VISIT";

    private static final String EXTRA_FIRST_NAME            = "EXTRA_FIRST_NAME";
    private static final String EXTRA_LAST_NAME             = "EXTRA_LAST_NAME";
    private static final String EXTRA_CNP                   = "EXTRA_CNP";
    private static final String EXTRA_ID_SERIES             = "EXTRA_ID_SERIES";
    private static final String EXTRA_PHOTO                 = "EXTRA_PHOTO";
    private static final String EXTRA_ELECTRONIC_SIGNATURE  = "EXTRA_ELECTRONIC_SIGNATURE";

    private enum DateType {
        DATETIME_START, DATETIME_END
    }

    private UpcomingVisitorPresenter presenter;

    @BindView(R.id.txtFirstName)
    protected TextView txtFirstName;

    @BindView(R.id.txtLastName)
    protected TextView txtLastName;

    @BindView(R.id.txtVisitStart)
    protected TextView txtVisitStart;

    @BindView(R.id.btnTimeStart)
    protected View btnTimeStart;

    private Calendar dateStart = Calendar.getInstance();
    private Calendar timeStart = null;

    @BindView(R.id.txtVisitEnd)
    protected TextView txtVisitEnd;

    @BindView(R.id.btnTimeEnd)
    protected View btnTimeEnd;

    private Calendar dateEnd = Calendar.getInstance();
    private Calendar timeEnd = null;

    private DatePickerDialog datePickerDialog;
    private DateType datePickerTypeShown = null;
    private Calendar datePicked = Calendar.getInstance();

    private TimePickerDialog timePickerDialog;
    private DateType timePickerTypeShown = null;
    private Calendar timePicked = Calendar.getInstance();

    @BindView(R.id.btnSelectRoom)
    protected TextView btnSelectRoom;

    @BindView(R.id.viewParticipants)
    protected ViewGroup viewParticipants;

    private List<Employee> listEmployees = new ArrayList<>();
    private Room selectedRoom = null;

    private UpcomingVisitorListener mUpcomingVisitorListener;

    public static UpcomingVisitorFragment newInstance(boolean isFullVisit) {
        UpcomingVisitorFragment fragment = new UpcomingVisitorFragment();

        Bundle args = new Bundle();
        args.putBoolean(EXTRA_FULL_VISIT, isFullVisit);
        fragment.setArguments(args);

        return fragment;
    }

    public static UpcomingVisitorFragment newInstance(String firstName,
                                                      String lastName,
                                                      String cnp,
                                                      String idSeries,
                                                      Bitmap photo,
                                                      Bitmap electronicSignature,
                                                      UpcomingVisitorListener listener) {
        UpcomingVisitorFragment fragment = new UpcomingVisitorFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_FIRST_NAME, firstName);
        args.putString(EXTRA_LAST_NAME, lastName);
        args.putString(EXTRA_CNP, cnp);
        args.putString(EXTRA_ID_SERIES, idSeries);
        args.putParcelable(EXTRA_PHOTO, photo);
        args.putParcelable(EXTRA_ELECTRONIC_SIGNATURE, electronicSignature);
        fragment.setArguments(args);

        fragment.setUpcomingVisitorListener(listener);

        return fragment;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.addVisitor);
    }

    @Override
    protected int layoutId() {
        return R.layout.component_visits_upcoming_visitor;
    }

    @Override
    protected void initView(View v) {
        presenter = new UpcomingVisitorPresenter(this);

        btnTimeStart.setEnabled(false);
        btnTimeEnd.setEnabled(false);

        showSelectedRoom();
        showSelectedEmployees();

        Bundle args = getArguments();

        if (args != null) {
            if (args.containsKey(EXTRA_FIRST_NAME)) {
                txtFirstName.setText(args.getString(EXTRA_FIRST_NAME));
                txtLastName.setText(args.getString(EXTRA_LAST_NAME));

                Calendar calendar = Calendar.getInstance();
                doOnTimeSet(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), txtVisitStart, dateStart);

                calendar.add(Calendar.HOUR, 1);
                doOnTimeSet(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), txtVisitEnd, dateEnd);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_done)
            doneClicked();

        return super.onOptionsItemSelected(item);
    }

    public void setUpcomingVisitorListener(UpcomingVisitorListener listener) {
        this.mUpcomingVisitorListener = listener;
    }

    @OnClick({R.id.btnCalendarStart, R.id.btnCalendarEnd})
    public void calendarClicked(View v) {
        if (datePickerTypeShown != null)
            return;

        datePickerTypeShown = v.getId() == R.id.btnCalendarStart ? DateType.DATETIME_START : DateType.DATETIME_END;

        Calendar calendar = Calendar.getInstance();

        if (datePickerDialog == null)
            datePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

//        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), (DialogInterface.OnClickListener) null);
//        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.OK), (DialogInterface.OnClickListener) null);
        datePickerDialog.setOnDismissListener(this);
        datePickerDialog.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        datePicked.set(Calendar.YEAR, year);
        datePicked.set(Calendar.MONTH, month);
        datePicked.set(Calendar.DAY_OF_MONTH, day);

        if (datePickerTypeShown == DateType.DATETIME_START) {
//            DateFormat formatter = new SimpleDateFormat(
//                    txtVisitStart.getText().length() == Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE.length()
//                            ? Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE : Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());

            DateFormat formatter = new SimpleDateFormat(timeStart == null
                    ? Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE : Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());

            if (timeStart != null) {
                datePicked.set(Calendar.HOUR, timeStart.get(Calendar.HOUR));
                datePicked.set(Calendar.MINUTE, timeStart.get(Calendar.MINUTE));
            }

            txtVisitStart.setText(formatter.format(datePicked.getTime()));
            dateStart.setTime(datePicked.getTime());
            btnTimeStart.setEnabled(true);
        }
        else {
            DateFormat formatter = new SimpleDateFormat(timeEnd == null
                    ? Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE : Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());
            if (timeEnd != null) {
                datePicked.set(Calendar.HOUR, timeEnd.get(Calendar.HOUR));
                datePicked.set(Calendar.MINUTE, timeEnd.get(Calendar.MINUTE));
            }

            txtVisitEnd.setText(formatter.format(datePicked.getTime()));
            dateEnd.setTime(datePicked.getTime());
            btnTimeEnd.setEnabled(true);
        }
    }


    @OnClick({R.id.btnTimeStart, R.id.btnTimeEnd})
    public void timeClicked(View v) {
        if (datePickerTypeShown != null)
            return;

        timePickerTypeShown = v.getId() == R.id.btnTimeStart ? DateType.DATETIME_START : DateType.DATETIME_END;

        Calendar calendar = Calendar.getInstance();

        if (timePickerDialog == null)
            timePickerDialog = new TimePickerDialog(getActivity(), this, 0, 0, false);

        timePickerDialog.setOnDismissListener(this);
        timePickerDialog.updateTime(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        if (timePickerTypeShown == DateType.DATETIME_START)
            doOnTimeSet(hour, minute, txtVisitStart, dateStart);
        else
            doOnTimeSet(hour, minute, txtVisitEnd, dateEnd);
    }

    private void doOnTimeSet(int hour, int minute, TextView txtDate, Calendar calendarToSet) {
        DateFormat formatter = new SimpleDateFormat(
                txtDate.getText().length() == Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE.length()
                        ? Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE : Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());

        try {
            Date date = formatter.parse(txtDate.getText().toString());
            datePicked.setTime(date);
            datePicked.set(Calendar.HOUR, hour);
            datePicked.set(Calendar.MINUTE, minute);

            formatter = new SimpleDateFormat(Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());
            txtDate.setText(formatter.format(datePicked.getTime()));
            calendarToSet.setTime(datePicked.getTime());
        } catch (ParseException e) {
            Log.e("upcoming", "error: " + e.getMessage());
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        datePickerTypeShown = null;
        timePickerTypeShown = null;
    }

    private Date getDateFromEditText(TextView txtDate) {
        DateFormat formatter = new SimpleDateFormat(
                txtDate.getText().length() == Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE.length()
                        ? Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE : Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());

        try {
            return formatter.parse(txtDate.getText().toString());
        }
        catch (Exception e) {}

        return null;
    }

    @OnClick(R.id.btnAddParticipant)
    public void addParticipantClicked() {
        EmployeesFragment fragment = new EmployeesFragment();
        fragment.setOnItemSelectListener((source, selectedEmployees) -> onEmployeesSelected(selectedEmployees));
        addFragment(fragment);
    }

    @OnClick(R.id.btnSelectRoom)
    public void selectRoomClicked() {
        RoomsFilterFragment fragment = new RoomsFilterFragment();
        fragment.setOnItemSelectListener((source, item) -> onRoomSelected(item));
        addFragment(fragment);
    }


    private void onRoomSelected(Room room) {
        this.selectedRoom = room;

        showSelectedRoom();
    }

    private void showSelectedRoom() {
        if (selectedRoom != null)
            btnSelectRoom.setText(String.format(Locale.getDefault(), "%s: %s, %s %d",
                    getResources().getString(R.string.room), selectedRoom.getName(),
                    getResources().getString(R.string.floor), selectedRoom.getFloor()));
        else
            btnSelectRoom.setText(R.string.placeholderSelectARoom);
    }

    private void onEmployeesSelected(List<Employee> employees) {

        listEmployees.clear();
        listEmployees.addAll(employees);

        showSelectedEmployees();
    }

    private void showSelectedEmployees() {
        viewParticipants.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        for (Employee employee : listEmployees) {
            TextView txtEmployee = (TextView) inflater.inflate(R.layout.component_single_participant, viewParticipants, false);
                    //new TextView(getActivity());
            txtEmployee.setText(employee.getPerson().getFullName());
            txtEmployee.setTag(employee);
            viewParticipants.addView(txtEmployee);

            txtEmployee.setOnClickListener(v -> {
                listEmployees.remove(v.getTag());
                showSelectedEmployees();
            });
        }
    }

    public void doneClicked() {
        // TODO validare

        AppDelegate.getInstance(getActivity()).hideKeyboard(getActivity());
        showLoadingDialog(null);

        Bundle args = getArguments();

        if (!args.containsKey(EXTRA_FIRST_NAME)) {
            presenter.saveVisit(txtFirstName.getText().toString(),
                    txtLastName.getText().toString(),
                    getDateFromEditText(txtVisitStart),
                    getDateFromEditText(txtVisitEnd),
                    selectedRoom,
                    listEmployees);
        }
        else {
            presenter.saveVisitAndCheckin(txtFirstName.getText().toString(),
                    txtLastName.getText().toString(),
                    getDateFromEditText(txtVisitStart),
                    getDateFromEditText(txtVisitEnd),
                    selectedRoom,
                    listEmployees,
                    args.getString(EXTRA_CNP),
                    args.getString(EXTRA_ID_SERIES),
                    (Bitmap) args.getParcelable(EXTRA_PHOTO),
                    (Bitmap) args.getParcelable(EXTRA_ELECTRONIC_SIGNATURE));
        }
    }

    @Override
    public void visitSaved() {
        dismissLoadingDialog();
        getActivity().onBackPressed();

        if (mUpcomingVisitorListener != null)
            mUpcomingVisitorListener.onFullVisitCreated();
    }
}
