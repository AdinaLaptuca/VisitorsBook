package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Visit;

import java.util.List;

public class VisitsContract {

    interface View extends MvpContract.View {
        void notifyDataChanged();
    }

    interface Presenter extends MvpContract.Presenter<View> {
        List<Visit> getVisits();
        void setSearchString(String query);

        void getData();

        void checkoutVisit(Visit visit);

        void removeVisit(Visit visit);
    }
}
