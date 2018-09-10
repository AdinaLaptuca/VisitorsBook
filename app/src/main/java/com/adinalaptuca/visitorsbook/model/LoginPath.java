package com.adinalaptuca.visitorsbook.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class LoginPath implements Parcelable {

    @SerializedName("path")
    @NonNull
    public abstract String getPath();

    @SerializedName("userID")
    @NonNull
    public abstract String getUserID();

    public static TypeAdapter<LoginPath> typeAdapter(Gson gson) {
        return new AutoValue_LoginPath.GsonTypeAdapter(gson);
    }

    public static LoginPath.Builder builder() {
        return new AutoValue_LoginPath.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setPath(@Nullable String path);
        public abstract Builder setUserID(@Nullable String userID);

        public abstract LoginPath build();
    }
}
