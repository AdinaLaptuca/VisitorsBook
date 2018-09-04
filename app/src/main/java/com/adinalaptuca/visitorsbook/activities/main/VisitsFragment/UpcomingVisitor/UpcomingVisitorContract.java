package com.adinalaptuca.visitorsbook.activities.main.VisitsFragment.UpcomingVisitor;

import android.graphics.Bitmap;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Employee;
import com.adinalaptuca.visitorsbook.model.Room;

import java.util.Date;
import java.util.List;

interface UpcomingVisitorContract {
    interface View extends MvpContract.View {
        void visitSaved();
    }

    interface Presenter extends MvpContract.Presenter<UpcomingVisitorContract.View> {
        void saveVisit(String firstName,
                       String lastName,
                       Date timeStart,
                       Date timeEnd,
                       Room room,
                       List<Employee> participants);

            void saveVisitAndCheckin(String firstName,
                                     String lastName,
                                     Date dateStart,
                                     Date dateEnd,
                                     Room room,
                                     List<Employee> listEmployees,
                                     String cnp,
                                     String idSeries,
                                     Bitmap photo,
                                     Bitmap electronicSignature);
    }
}
