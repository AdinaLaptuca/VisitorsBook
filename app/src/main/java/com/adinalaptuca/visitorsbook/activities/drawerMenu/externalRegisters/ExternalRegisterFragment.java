package com.adinalaptuca.visitorsbook.activities.drawerMenu.externalRegisters;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.Constants;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.VisitsFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Office;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

public class ExternalRegisterFragment extends BaseToolbarFragment implements ExternalRegisterContract.View,
        DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener {
    private ExternalRegisterPresenter presenter;

    @BindView(R.id.spinnerOffice)
    protected Spinner spinnerOffice;
    private ArrayAdapter<String> adapterOffice;

    private Calendar dateStart = Calendar.getInstance();
    private Calendar dateEnd = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private DateType datePickerTypeShown = null;
    private Calendar datePicked = Calendar.getInstance();

    @BindView(R.id.txtVisitStart)
    protected TextView txtVisitStart;

    @BindView(R.id.txtVisitEnd)
    protected TextView txtVisitEnd;

    private enum DateType {
        DATETIME_START, DATETIME_END
    }

    @Override
    public String getToolbarTitle() {
        return getResources().getString(R.string.external_registers);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_external_register;
    }

    @Override
    protected void initView(View v) {
        presenter = new ExternalRegisterPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.fetchOffices();
    }

    @Override
    public void onResume() {
        super.onResume();

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_done) {
            doneClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void officesFetched(List<Office> offices) {
        List<String> officeNames = offices.stream()
                .map(office -> office.getFullName())
                .collect(Collectors.toList());

        adapterOffice = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, officeNames);
        adapterOffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOffice.setAdapter(adapterOffice);
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
            DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE, Locale.getDefault());

            txtVisitStart.setText(formatter.format(datePicked.getTime()));
            dateStart.setTime(datePicked.getTime());
        }
        else {
            DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMATTER_PATTERN_UPCOMING_VISITOR_DATE, Locale.getDefault());

            txtVisitEnd.setText(formatter.format(datePicked.getTime()));
            dateEnd.setTime(datePicked.getTime());
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        datePickerTypeShown = null;
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


    private void doneClicked() {
        AppDelegate.getInstance(getActivity()).hideKeyboard(getActivity());

        for (TextView dateField : Arrays.asList(txtVisitStart, txtVisitEnd)) {
            if (getDateFromEditText(dateField) == null) {
                dateField.requestFocus();
                dateField.setError(getResources().getString(R.string.cant_be_empty));
                return;
            }
        }

        addFragment(VisitsFragment.newInstace(presenter.getOfficeRefferenceId(spinnerOffice.getSelectedItemPosition()),
                getDateFromEditText(txtVisitStart),
                getDateFromEditText(txtVisitEnd)));
    }
}
