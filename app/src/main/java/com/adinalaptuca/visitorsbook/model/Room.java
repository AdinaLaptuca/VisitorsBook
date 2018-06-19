package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Room implements Parcelable {
    @Nullable public abstract String getRoom();
    @Nullable public abstract String getFloor();

    public static Builder builder() {
        return new AutoValue_Room.Builder();
    }

    public static TypeAdapter<Room> typeAdapter(Gson gson) {
        return new AutoValue_Room.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setRoom(String room);
        public abstract Builder setFloor(String floor);

        public abstract Room build();
    }
}
