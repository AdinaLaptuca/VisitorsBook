package com.example.adinalaptuca.visitorsbook.activities.main.RoomsFragment;

import com.example.adinalaptuca.visitorsbook.activities.main.EmployeesFragment.EmployeesContract;
import com.example.adinalaptuca.visitorsbook.custom.MvpContract;
import com.example.adinalaptuca.visitorsbook.model.Employee;
import com.example.adinalaptuca.visitorsbook.model.Room;

import java.util.List;

public class RoomsContract {
    interface View extends MvpContract.View {
        void notifyDataChanged();
    }

    interface Presenter extends MvpContract.Presenter<RoomsContract.View> {
        List<Room> getRooms();

        void getData();
    }
}
