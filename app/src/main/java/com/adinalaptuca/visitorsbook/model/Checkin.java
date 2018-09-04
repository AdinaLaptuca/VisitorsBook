package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

@AutoValue
public abstract class Checkin implements Parcelable {

    @SerializedName("timeStart")
    public abstract Date getTimeStart();

    @Nullable
    @SerializedName("timeEnd")
    public abstract Date getTimeEnd();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_Checkin.Builder();
    }

    public static TypeAdapter<Checkin> typeAdapter(Gson gson) {
        return new AutoValue_Checkin.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTimeStart(Date timeStarted);
        public abstract Builder setTimeEnd(Date timeEnded);

        public abstract Checkin build();
    }
}
