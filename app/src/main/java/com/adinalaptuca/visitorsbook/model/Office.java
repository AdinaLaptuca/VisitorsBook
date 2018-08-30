package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.firebase.firestore.Exclude;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@AutoValue
public abstract class Office implements Parcelable {
    public static final String SERIALIZE_REFERENCE_ID = "refID";
    public static final String SERIALIZE_ROOMS = "rooms";
    public static final String SERIALIZE_VISITS = "visits";

    @SerializedName("fullName")
    @Nullable
    public abstract String getFullName();

    @SerializedName("address")
    @Nullable
    public abstract String getAddress();

    @SerializedName("employees")
    @Nullable
    public abstract List<Employee> getEmployees();

    @SerializedName(SERIALIZE_REFERENCE_ID)
    @Nullable
    @Exclude
    public abstract String getReferenceID();

    public static Builder builder() {
        return new AutoValue_Office.Builder();
    }

    public static TypeAdapter<Office> typeAdapter(Gson gson) {
        return new AutoValue_Office.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setFullName(@Nullable String fullname);
        public abstract Builder setAddress(@Nullable String address);
        public abstract Builder setEmployees(@Nullable List<Employee> employees);
        public abstract Builder setReferenceID(String referenceID);

        public abstract Office build();
    }
}
