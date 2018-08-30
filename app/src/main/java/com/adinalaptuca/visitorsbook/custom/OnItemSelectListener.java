package com.adinalaptuca.visitorsbook.custom;

public interface OnItemSelectListener<T> {
    void onItemSelected(Object source, T item);
}