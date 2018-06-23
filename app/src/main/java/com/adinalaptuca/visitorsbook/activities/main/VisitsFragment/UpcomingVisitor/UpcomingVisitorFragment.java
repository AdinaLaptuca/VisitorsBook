package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.Presenter;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.VisitsAdapter;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class UpcomingVisitorFragment extends BaseToolbarFragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DialogInterface.OnDismissListener {

    private enum DateType {
        DATETIME_START, DATETIME_END
    }

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

    @Override
    public String getToolbarTitle() {
        return getString(R.string.addVisitor);
    }

    @Override
    protected int layoutId() {
        return R.layout.component_visits_upcoming_visitor;
    }

    protected void initView() {
        btnTimeStart.setEnabled(false);
        btnTimeEnd.setEnabled(false);
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
        Date date = null;

        if (timePickerTypeShown == DateType.DATETIME_START) {
            DateFormat formatter = new SimpleDateFormat(
                    txtVisitStart.getText().length() == Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE.length()
                            ? Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE : Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());

            try {
                date = formatter.parse(txtVisitStart.getText().toString());
                datePicked.setTime(date);
                datePicked.set(Calendar.HOUR, hour);
                datePicked.set(Calendar.MINUTE, minute);

                formatter = new SimpleDateFormat(Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());
                txtVisitStart.setText(formatter.format(datePicked.getTime()));
                dateStart.setTime(datePicked.getTime());
            } catch (ParseException e) {
                Log.e("upcoming", "error: " + e.getMessage());
            }
        }
        else {
            DateFormat formatter = new SimpleDateFormat(
                    txtVisitEnd.getText().length() == Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE.length()
                            ? Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE : Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());

            try {
                date = formatter.parse(txtVisitEnd.getText().toString());
                datePicked.setTime(date);
                datePicked.set(Calendar.HOUR, hour);
                datePicked.set(Calendar.MINUTE, minute);

                formatter = new SimpleDateFormat(Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE_TIME, Locale.getDefault());
                txtVisitEnd.setText(formatter.format(datePicked.getTime()));
                dateEnd.setTime(datePicked.getTime());
            } catch (ParseException e) {
                Log.e("upcoming", "error: " + e.getMessage());
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        datePickerTypeShown = null;
        timePickerTypeShown = null;
    }
}
