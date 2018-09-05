package com.adinalaptuca.visitorsbook.model;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.google.auto.value.AutoValue;
import com.google.firebase.firestore.Exclude;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@AutoValue
public abstract class Visit implements Parcelable, Comparable<Visit>
{
    @Exclude
    @Nullable
    public abstract String getId();

    @NonNull
    @SerializedName("timeStart")
    public abstract Date getTimeStart();

    @Nullable
    @SerializedName("timeEnd")
    public abstract Date getTimeEnd();

    @NonNull
    @SerializedName("person")
    public abstract Person getPerson();

    @Nullable
    @SerializedName("checkin")
    public abstract Checkin getCheckin();

    @SerializedName("participants")
    public abstract List<Employee> getParticipants();

    @Nullable
    @SerializedName("room")
    public abstract Room getRoom();

    @Exclude
    public String getReferenceId(Context context) {
        return String.format(Locale.getDefault(), "%s/%s/%s",
                AppDelegate.getInstance(context).getLoginPath(),
                Office.SERIALIZE_VISITS,
                getId());
    }

    public static Builder builder() {
        return new AutoValue_Visit.Builder()
                .setParticipants(new ArrayList<>());
    }

    public abstract Builder toBuilder();

    public static TypeAdapter<Visit> typeAdapter(Gson gson) {
        return new AutoValue_Visit.GsonTypeAdapter(gson);
    }
    
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String id);
        public abstract Builder setTimeStart(Date timeStarted);
        public abstract Builder setTimeEnd(Date timeEnded);
        public abstract Builder setCheckin(Checkin checkin);
        public abstract Builder setPerson(Person person);
        public abstract Builder setParticipants(List<Employee> participants);
        public abstract Builder setRoom(Room room);

        public abstract Visit build();
    }

    @Override
    public int compareTo(@NonNull Visit visit) {
        return getTimeStart().compareTo(visit.getTimeStart());
    }
}
