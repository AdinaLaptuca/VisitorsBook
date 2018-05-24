package com.example.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import com.example.adinalaptuca.visitorsbook.custom.MvpContract;
import com.example.adinalaptuca.visitorsbook.model.Employee;
import com.example.adinalaptuca.visitorsbook.model.Visit;

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
