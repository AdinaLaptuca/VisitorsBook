package com.example.adinalaptuca.visitorsbook.custom;

import android.content.Context;

/**
 * Copyright @ Julien Arzul 2016
 */

public interface MvpContract
{
    /**
     * Represents a View in MVP.
     */
    interface View
    {
        Context getContext();

        void showLoadingDialog(String message);
        void dismissLoadingDialog();

        void showToast(String message);
    }

    /**
     * Represents a Presenter in MVP.
     * <p>
     * By default, it can attach and detach its View.
     *
     * @param <T> The type of the View that the Presenter will handle
     */
    interface Presenter<T extends View>
    {
//        void attachView(T view);
//        void detachView();
    }
}
