package com.adinalaptuca.visitorsbook.model;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class AutoValueGsonTypeAdapterFactory implements TypeAdapterFactory {

    public static @NonNull TypeAdapterFactory create() {
        return new AutoValueGson_AutoValueGsonTypeAdapterFactory();
    }

    public static @NonNull Gson autovalueGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(AutoValueGsonTypeAdapterFactory.create())
                .create();
    }

}

