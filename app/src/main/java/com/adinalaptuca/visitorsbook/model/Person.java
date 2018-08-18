package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

@AutoValue
public abstract class Person implements Parcelable
{
    @SerializedName("firstName")
    public abstract String getFirstName();

    @SerializedName("lastName")
    public abstract String getLastName();

    @Nullable
    @SerializedName("cnp")
    public abstract String getCNP();

    @Nullable
    @SerializedName("serialNumber")
    public abstract String getSerialNumber();

    public String getFullName() {
        return String.format(Locale.getDefault(), "%s %s", getFirstName(), getLastName());
    }

    public static Builder builder() {
        return new AutoValue_Person.Builder();
    }

    public static TypeAdapter<Person> typeAdapter(Gson gson) {
        return new AutoValue_Person.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setFirstName(String firstName);
        public abstract Builder setLastName(String lastName);
        public abstract Builder setCNP(@Nullable String cnp);
        public abstract Builder setSerialNumber(@Nullable String serialNumber);

        public abstract Person build();
    }
}