package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Employee implements Parcelable {
    @Nullable public abstract String firstName();
    @Nullable public abstract String lastName();
    @Nullable public abstract String function();

    public static Employee.Builder builder() {
        return new AutoValue_Employee.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setFirstName(@Nullable String firstName);
        public abstract Builder setLastName(@Nullable String lastName);
        public abstract Builder setFunction(@Nullable String function);

        public abstract Employee build();
    }
}
