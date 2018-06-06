package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Employee;

import java.util.List;

public class EmployeesContract {

    interface View extends MvpContract.View {
        void notifyDataChanged();
    }

    interface Presenter extends MvpContract.Presenter<View> {
        List<Employee> getEmployees();

        void getData();
    }
}
