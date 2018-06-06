package com.adinalaptuca.visitorsbook.activities.main.RoomsFragment;

import com.adinalaptuca.visitorsbook.model.Room;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

public class Presenter implements RoomsContract.Presenter {

    private RoomsContract.View view;
    private List<Room> listRooms = new ArrayList<>();

    Presenter(RoomsContract.View view) {
        this.view = view;
    }

    @Override
    public List<Room> getRooms() {
        return listRooms;
    }

    @Override
    public void getData() {
        Observable.just(
                Room.builder().setRoom("Selena").setFloor("5th floor").build(),
                Room.builder().setRoom("Moonwalk").setFloor("La beci").build(),
                Room.builder().setRoom("Beatles").setFloor("7th floor").build(),
                Room.builder().setRoom("Materhorn").setFloor("6th floor").build(),
                Room.builder().setRoom("Arsenal").setFloor("5th floor").build(),
                Room.builder().setRoom("Invincibles").setFloor("1th floor").build(),
                Room.builder().setRoom("Hebe").setFloor("ground floor").build(),
                Room.builder().setRoom("Success").setFloor("underground").build(),
                Room.builder().setRoom("Victory").setFloor("5th floor").build()
        )
                .asObservable()
                .subscribe(room -> {
                    listRooms.add(room);
                    view.notifyDataChanged();
                });
    }
}
