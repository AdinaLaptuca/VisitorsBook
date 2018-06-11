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
    @SerializedName("firstName")
    @Nullable
    public abstract String getFirstName();

    @SerializedName("lastName")
    @Nullable
    public abstract String getLastName();

    @SerializedName("function")
//    @PropertyName("function")
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

    public static Employee map2Object(Map<String, Object> map) {
        return Employee.builder()
                .setFirstName((String) map.get("firstname"))
                .setLastName((String) map.get("lastName"))
                .setFunction((String) map.get("function")).build();
    }
}
