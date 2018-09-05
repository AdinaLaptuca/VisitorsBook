package com.adinalaptuca.visitorsbook.activities.drawerMenu.externalRegisters;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Office;

import java.util.List;

interface ExternalRegisterContract {
    interface View extends MvpContract.View {
        void officesFetched(List<Office>  offices);
    }

    interface Presenter extends MvpContract.Presenter<ExternalRegisterContract.View> {
        void fetchOffices();
    }
}
