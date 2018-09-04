package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.PreviewVisitorData;

import android.graphics.Bitmap;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Visit;

interface PreviewVisitorContract {
    interface View extends MvpContract.View {

        void checkinDone();
    }

    interface Presenter extends MvpContract.Presenter<PreviewVisitorContract.View> {

        void doCheckin(Visit visit,
                       String cnp,
                       String idSerial,
                       Bitmap photo,
                       Bitmap signature);
    }
}
