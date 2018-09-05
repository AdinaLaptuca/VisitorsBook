package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Employee;

import java.util.List;

interface EmployeesContract {

    interface View extends MvpContract.View {
        void employeesFetched();
    }

    interface Presenter extends MvpContract.Presenter<EmployeesContract.View> {
        List<Employee> getEmployees();

        void fetchEmployees();

        void searchEmployees(String filter);
    }
}
