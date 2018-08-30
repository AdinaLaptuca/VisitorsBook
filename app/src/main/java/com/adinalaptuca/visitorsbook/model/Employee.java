package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.firebase.firestore.PropertyName;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

@AutoValue
public abstract class Employee implements Parcelable {

    @SerializedName("email")
    @Nullable
    public abstract String getEmail();

    @SerializedName("person")
    public abstract Person getPerson();

    @SerializedName("userID")
    @Nullable
    public abstract String getUserID();

    @SerializedName("employeeRole")
    @Nullable
    public abstract EmployeeRole getEmployeeRole();

    public static Employee.Builder builder() {
        return new AutoValue_Employee.Builder();
    }

    public static TypeAdapter<Employee> typeAdapter(Gson gson) {
        return new AutoValue_Employee.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setEmail(@Nullable String email);
        public abstract Builder setPerson(Person person);
        public abstract Builder setEmployeeRole(@Nullable EmployeeRole role);
        public abstract Builder setUserID(@Nullable String userID);

        public abstract Employee build();
    }
}
