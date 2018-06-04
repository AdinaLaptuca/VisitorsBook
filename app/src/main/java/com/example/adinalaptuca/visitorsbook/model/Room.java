package com.example.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Room implements Parcelable {
    @Nullable public abstract String getRoom();
    @Nullable public abstract String getFloor();

    public static Room.Builder builder() {
        return new AutoValue_Room.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setRoom(String room);
        public abstract Builder setFloor(String floor);

        public abstract Room build();
    }
}
