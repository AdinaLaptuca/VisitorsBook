package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Office implements Parcelable {
    @SerializedName("fullName")
    @Nullable
    public abstract String getFullname();

    @SerializedName("address")
    @Nullable
    public abstract String getAddress();

    @SerializedName("employees")
    @Nullable
    public abstract List<Employee> getEmployees();

    public static Builder builder() {
        return new AutoValue_Office.Builder();
    }

    public static TypeAdapter<Office> typeAdapter(Gson gson) {
        return new AutoValue_Office.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setFullname(@Nullable String fullname);
        public abstract Builder setAddress(@Nullable String address);
        public abstract Builder setEmployees(@Nullable List<Employee> employees);

        public abstract Office build();
    }
}
