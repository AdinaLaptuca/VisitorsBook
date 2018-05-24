package com.example.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
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

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setDateOfVisit(Date dateOfVisit);
        public abstract Builder setTimeStarted(Date timeStarted);
        public abstract Builder setTimeEnded(Date timeEnded);
        public abstract Builder setName(String name);

        public abstract Visit build();
    }
}
