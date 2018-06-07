package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.firebase.firestore.PropertyName;

@AutoValue
public abstract class Employee implements Parcelable {
    @PropertyName("firstName")
    @Nullable
    public abstract String getFirstName();

    @PropertyName("lastName")
    @Nullable
    public abstract String getLastName();

    @PropertyName("function")
    @Nullable
    public abstract String getFunction();

    public static Employee.Builder builder() {
        return new AutoValue_Employee.Builder();
    }

//    public static TypeAdapter<Employee> typeAdapter(Gson gson) {
//        return new AutoValue_Employee.GsonTypeAdapter(gson);
//    }
//
//    public static TypeAdapter<Employee> typeAdapter(Gson gson) {
//        return new Employee_GsonTypeAdapter(gson);
//    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setFirstName(@Nullable String firstName);
        public abstract Builder setLastName(@Nullable String lastName);
        public abstract Builder setFunction(@Nullable String function);

        public abstract Employee build();
    }
}
