package com.adinalaptuca.visitorsbook.activities.main.room.RoomsFilter;

import com.adinalaptuca.visitorsbook.custom.MvpContract;
import com.adinalaptuca.visitorsbook.model.Room;

import java.util.ArrayList;
import java.util.List;

class RoomsFilterContract {
    interface View extends MvpContract.View {
        void floorsFetched();

        void utilitiesFetched();

        void searchResultsFetched(ArrayList<Room> result);
    }

    interface Presenter extends MvpContract.Presenter<RoomsFilterContract.View> {
        List<Integer> getListFloors();
        void fetchFloors();

        List<String> getUtilites();
        void fetchUtilites();

        void searchRooms(Integer noSpots, Integer floor, List<String> utilities);
    }
}
