package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class Company implements Parcelable {

    public static final String SERIALIZE_NAME_SHORT_NAME = "shortName";

    @Nullable
    public abstract String getName();

    @SerializedName("getFullName")
    @Nullable
    public abstract String getFullname();

    @SerializedName(SERIALIZE_NAME_SHORT_NAME)
    @Nullable
    public abstract String getShortname();

    @SerializedName("offices")
    public abstract List<Office> getOffices();

    public static Builder builder() {
        return new AutoValue_Company.Builder()
                .setOffices(new ArrayList<>());
    }

    public static TypeAdapter<Company> typeAdapter(Gson gson) {
        return new AutoValue_Company.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setFullname(@Nullable String fullname);
        public abstract Builder setShortname(@Nullable String fullname);
        public abstract Builder setName(@Nullable String name);
        public abstract Builder setOffices(@Nullable List<Office> employees);

        public abstract Company build();
    }
}
