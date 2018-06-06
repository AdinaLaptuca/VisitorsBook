package com.adinalaptuca.visitorsbook.activities.main.RoomsFragment;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Room;
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
