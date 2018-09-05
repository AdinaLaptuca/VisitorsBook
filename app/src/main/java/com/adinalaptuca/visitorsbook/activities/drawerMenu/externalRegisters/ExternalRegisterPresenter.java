package com.adinalaptuca.visitorsbook.activities.drawerMenu.externalRegisters;

import com.adinalaptuca.visitorsbook.model.Office;

import java.util.ArrayList;
import java.util.List;

class ExternalRegisterPresenter implements ExternalRegisterContract.Presenter {
    private ExternalRegisterContract.View view;

    List<Office> listOffices = new ArrayList<>();

    ExternalRegisterPresenter(ExternalRegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchOffices() {
        view.officesFetched(listOffices);
    }
}
