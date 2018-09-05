package com.adinalaptuca.visitorsbook.model;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.adinalaptuca.visitorsbook.R;
import com.google.auto.value.AutoValue;
import com.google.firebase.firestore.Exclude;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AutoValue
public abstract class Room implements Parcelable {

    public static final String SMART_FLIPCHART = "Smart flipchart";
    public static final String FLIPCHART = "Flipchart";
    public static final String VIDEO_PROJECTOR = "Video projector";
    public static final String CONFERENCE_PHONE = "Conference phone";
    public static final String WHITEBOARD = "Whiteboard";

    public static final String SERIALIZE_NAME_NO_SPOTS = "noSpots";
    public static final String SERIALIZE_NAME_FLOOR = "floor";
    public static final String SERIALIZE_NAME_UTILITIES = "utilities";

    @SerializedName("name")
    @Nullable public abstract String getName();

    @SerializedName(SERIALIZE_NAME_FLOOR)
    public abstract int getFloor();

    @SerializedName(SERIALIZE_NAME_NO_SPOTS)
    public abstract int getNoSpots();

    @SerializedName(SERIALIZE_NAME_UTILITIES)
    public abstract List<String> getUtilities();

    @Exclude
    public String toString(Context context) {
        return String.format(Locale.getDefault(), "%s: %s, %s %d",
                context.getResources().getString(R.string.room), getName(),
                context.getResources().getString(R.string.floor), getFloor());
    }

    public static Builder builder() {
        return new AutoValue_Room.Builder().setUtilities(new ArrayList<>());
    }

    public static TypeAdapter<Room> typeAdapter(Gson gson) {
        return new AutoValue_Room.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setName(String room);
        public abstract Builder setFloor(int floor);
        public abstract Builder setNoSpots(int noSpots);
        public abstract Builder setUtilities(List<String> utilities);

        public abstract Room build();
    }
}
