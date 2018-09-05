package com.adinalaptuca.visitorsbook.activities.drawerMenu.externalRegisters;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Office;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ExternalRegisterFragment extends BaseToolbarFragment implements ExternalRegisterContract.View {
    private ExternalRegisterPresenter presenter;

    @BindView(R.id.spinnerOffice)
    protected Spinner spinnerOffice;
    private ArrayAdapter<String> adapterOffice;

    @BindView(R.id.txtVisitStart)
    protected TextView txtVisitStart;

    @BindView(R.id.txtVisitEnd)
    protected TextView txtVisitEnd;

    @Override
    public String getToolbarTitle() {
        return getResources().getString(R.string.external_registers);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_external_register;
    }

    @OnClick({R.id.btnCalendarStart, R.id.btnCalendarEnd})
    public void asd() {

    }

    @Override
    public void officesFetched(List<Office> offices) {
//        List

//        adapterOffice = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, offices);
//        adapterOffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerOffice.setAdapter(adapterOffice);
    }
}
