package com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import com.example.adinalaptuca.visitorsbook.custom.MvpContract;
import com.example.adinalaptuca.visitorsbook.model.Visit;

import java.util.List;

public class VisitsContract {

    interface View extends MvpContract.View {
        void notifyDataChanged();
    }

    interface Presenter extends MvpContract.Presenter<View> {
        List<Visit> getVisits();

        void getData();
    }
}
