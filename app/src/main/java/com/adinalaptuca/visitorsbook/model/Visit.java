package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Date;

@AutoValue
public abstract class Visit implements Parcelable
{
    public abstract Date dateOfVisit();
    public abstract Date timeStarted();
    public abstract Date timeEnded();
    public abstract String name();

    public static Builder builder() {
        return new AutoValue_Visit.Builder();
    }

    public static TypeAdapter<Visit> typeAdapter(Gson gson) {
        return new AutoValue_Visit.GsonTypeAdapter(gson);
    }
    
    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setDateOfVisit(Date dateOfVisit);
        public abstract Builder setTimeStarted(Date timeStarted);
        public abstract Builder setTimeEnded(Date timeEnded);
        public abstract Builder setName(String name);

        public abstract Visit build();
    }
}
