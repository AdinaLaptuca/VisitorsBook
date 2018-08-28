package com.adinalaptuca.visitorsbook.activities.main.room.RoomsFilter;

import android.app.Activity;
import android.util.Log;
import android.widget.CheckBox;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.model.AutoValueGsonTypeAdapterFactory;
import com.adinalaptuca.visitorsbook.model.Office;
import com.adinalaptuca.visitorsbook.model.Room;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Presenter implements RoomsFilterContract.Presenter {
    private RoomsFilterContract.View view;

    private Set<Integer> listFloors = new HashSet<>();
    private List<String> listUtilities = new ArrayList<>();

    public Presenter(RoomsFilterContract.View view) {
        this.view = view;
    }

    @Override
    public List<Integer> getListFloors() {
        List<Integer> floors = new ArrayList<>(listFloors);
        Collections.sort(floors);
        return floors;
    }

    @Override
    public void fetchFloors() {
        String path = String.format(Locale.getDefault(), "%s/%s",
                AppDelegate.getInstance(view.getContext()).getLoginPath(),
                Office.SERIALIZE_ROOMS);

        FirebaseFirestore.getInstance().collection(path)
                .get()
                .addOnCompleteListener((Activity) view.getContext(), task -> {
                   if (task.isSuccessful()) {
                       Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();
                       List<Room> rooms = new ArrayList<>();

                       for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                           if (!snapshot.exists() || snapshot.getData() == null)
                               continue;

                           Map<String, Object> data = snapshot.getData();
                           Room room = gson.fromJson(gson.toJson(data), Room.class);
                           rooms.add(room);
                       }

                       listFloors.clear();

                       listFloors.addAll(
                           rooms.stream()
                                   .map(room -> room.getFloor())
                                   .collect(Collectors.toList()));

                       view.floorsFetched();
                   }
                });
    }

    @Override
    public List<String> getUtilites() {
        return listUtilities;
    }

    @Override
    public void fetchUtilites() {
        FirebaseFirestore.getInstance().collection("roomsUtilities")
                .addSnapshotListener((Activity) view.getContext(), (querySnapshot, e) -> {
                    if (e == null) {
                        listUtilities.clear();

                        for (DocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                            listUtilities.add(snapshot.getString("name"));
                        }

                        view.utilitiesFetched();
                    }
                });
    }

    @Override
    public void searchRooms(Integer noSpots, Integer floorIndex, List<String> utilities) {
        String path = String.format(Locale.getDefault(), "%s/%s",
                AppDelegate.getInstance(view.getContext()).getLoginPath(),
                Office.SERIALIZE_ROOMS);

        Query query = FirebaseFirestore.getInstance().collection(path)
                .whereLessThan(Room.SERIALIZE_NAME_NO_SPOTS, noSpots);

        query.addSnapshotListener((Activity) view.getContext(), (querySnapshot, e) -> {
            if (e == null)
            {
                Gson gson = AutoValueGsonTypeAdapterFactory.autovalueGson();
                List<Room> rooms = new ArrayList<>();

                for (DocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                    rooms.add(gson.fromJson(gson.toJson(snapshot.getData()), Room.class));
                }

                view.searchResultsFetched(
                        new ArrayList<>(rooms.stream()
                            .filter(room -> room.getUtilities().containsAll(utilities))
                            .filter(room -> (floorIndex == null || room.getFloor() == getListFloors().get(floorIndex)))
                            .collect(Collectors.toList())));
            }
        });
    }
}
