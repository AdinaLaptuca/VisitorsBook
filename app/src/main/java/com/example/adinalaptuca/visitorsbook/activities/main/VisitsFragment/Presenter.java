package com.example.adinalaptuca.visitorsbook.activities.main.VisitsFragment;

import android.util.Log;

import com.example.adinalaptuca.visitorsbook.model.Visit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

public class Presenter implements VisitsContract.Presenter {

    private VisitsContract.View view;

    private List<Visit> listVisits = new ArrayList<>();

    Presenter(VisitsContract.View view) {
        this.view = view;
    }

    @Override
    public List<Visit> getVisits() {
        return listVisits;
    }

    @Override
    public void getData() {

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.add(Calendar.MINUTE, -3);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.add(Calendar.MINUTE, 6);

        Observable.just(
                Visit.builder()
                        .setDateOfVisit(new Date())
                        .setName("John Smith")
                        .setTimeStarted(calendarStart.getTime())
                        .setTimeEnded(calendarEnd.getTime())
                        .build(),
                Visit.builder().setDateOfVisit(new Date()).setName("John Doe").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build(),
                Visit.builder().setDateOfVisit(new Date()).setName("Duncan McCloud").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build(),
                Visit.builder().setDateOfVisit(new Date()).setName("John Wick").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build(),
                Visit.builder().setDateOfVisit(new Date()).setName("Indiana jones").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build(),
                Visit.builder().setDateOfVisit(new Date()).setName("Amy Winehouse").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build(),
                Visit.builder().setDateOfVisit(new Date()).setName("Han Solo").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build(),
                Visit.builder().setDateOfVisit(new Date()).setName("Chewbaka").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build(),
                Visit.builder().setDateOfVisit(new Date()).setName("Darth Vader").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build(),
                Visit.builder().setDateOfVisit(new Date()).setName("Cpt Pickard").setTimeStarted(calendarStart.getTime()).setTimeEnded(calendarEnd.getTime()).build()
        )
                .asObservable()
                .subscribe(new Action1<Visit>() {
                    @Override
                    public void call(Visit visit) {
                        listVisits.add(visit);
                        view.notifyDataChanged();
                    }
                });
    }
}
