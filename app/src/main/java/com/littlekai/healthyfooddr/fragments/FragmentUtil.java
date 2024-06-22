package com.littlekai.healthyfooddr.fragments;

import android.graphics.Bitmap;

import com.littlekai.healthyfooddr.model.Food;

public interface FragmentUtil {
    void showDishImage(Bitmap bitmap);

    void fragmentBegin(Food food);
}
