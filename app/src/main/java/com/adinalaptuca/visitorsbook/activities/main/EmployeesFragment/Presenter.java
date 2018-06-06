package com.adinalaptuca.visitorsbook.activities.main.EmployeesFragment;

import com.adinalaptuca.visitorsbook.model.Employee;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;

public class Presenter implements EmployeesContract.Presenter {

    private EmployeesContract.View view;

    private List<Employee> listEmployees = new ArrayList<>();

    Presenter(EmployeesContract.View view) {
        this.view = view;
    }

    @Override
    public List<Employee> getEmployees() {
        return listEmployees;
    }

    @Override
    public void getData() {

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.add(Calendar.MINUTE, -3);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.add(Calendar.MINUTE, 6);

        Observable.just(
                Employee.builder().setFirstName("John").setLastName("Smith").setFunction("Lead dev").build(),
                Employee.builder().setFirstName("John").setLastName("Doe").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Duncan").setLastName("McCloud").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("John").setLastName("Wick").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Indiana").setLastName("jones").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Han").setLastName("Solo").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Chewbaka").setFunction("Junior dev").build(),
                Employee.builder().setFirstName("Darth").setLastName("Vader").setFunction("Junior dev").build()
        )
                .asObservable()
                .subscribe(new Action1<Employee>() {
                    @Override
                    public void call(Employee employee) {
                        listEmployees.add(employee);
                        view.notifyDataChanged();
                    }
                });
    }
}
