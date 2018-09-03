package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
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

    private static final String EXTRA_FULL_VISIT    = "EXTRA_FULL_VISIT";

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

    public static UpcomingVisitorFragment newInstance(boolean isFullVisit) {
        UpcomingVisitorFragment fragment = new UpcomingVisitorFragment();

        Bundle args = new Bundle();
        args.putBoolean(EXTRA_FULL_VISIT, isFullVisit);
        fragment.setArguments(args);

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            Log.e("upcoming", "1, onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null)
            Log.e("upcoming", "2, onViewCreated: " + savedInstanceState.getString("txtVisitStart", ""));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
Log.e("upcoming", "onSaveInstanceState");
        outState.putString("txtVisitStart", txtVisitStart.getText().toString());
        outState.putString("txtVisitEnd", txtVisitEnd.getText().toString());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null)
            Log.e("upcoming", "onViewStateRestored");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            Log.e("upcoming", "savedInstanceState");
        }
    }

    @Override
    protected void initView(View v) {
        presenter = new UpcomingVisitorPresenter(this);

        btnTimeStart.setEnabled(false);
        btnTimeEnd.setEnabled(false);

        showSelectedRoom();
        showSelectedEmployees();
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

    @OnClick(R.id.btnFinish)
    public void saveClicked() {
        AppDelegate.getInstance(getActivity()).hideKeyboard(getActivity());
        showLoadingDialog(null);

        presenter.saveVisit(txtFirstName.getText().toString(),
                txtLastName.getText().toString(),
                getDateFromEditText(txtVisitStart),
                getDateFromEditText(txtVisitEnd),
                selectedRoom,
                listEmployees);
    }

    @Override
    public void visitSaved() {
        dismissLoadingDialog();
        getActivity().onBackPressed();
    }
}
