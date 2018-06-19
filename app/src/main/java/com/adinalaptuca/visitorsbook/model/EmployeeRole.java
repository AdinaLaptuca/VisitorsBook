package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class EmployeeRole implements Parcelable {

    public static final String SERIALIZE_NAME_ROLE = "role";

    @SerializedName("role")
    @Nullable
    public abstract String getRole();

    @SerializedName("accessType")
    public abstract int getAccessType();

    @SerializedName("roleName")
    @Nullable
    public abstract String getRoleName();

    public static Builder builder() {
        return new AutoValue_EmployeeRole.Builder();
    }

    public static TypeAdapter<EmployeeRole> typeAdapter(Gson gson) {
        return new AutoValue_EmployeeRole.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setRole(String role);
        public abstract Builder setAccessType(int accessType);
        public abstract Builder setRoleName(String description);

        public abstract EmployeeRole build();
    }
}
