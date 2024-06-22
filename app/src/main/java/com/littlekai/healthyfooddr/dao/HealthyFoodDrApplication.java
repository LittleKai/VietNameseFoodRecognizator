package com.littlekai.healthyfooddr.dao;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

public class HealthyFoodDrApplication extends Application {
    Bitmap capturedBm;
    private static Context mContext;

    private static HealthyFoodDrApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }
    public static Context getAppContext() {
        return mContext;
    }
    public Bitmap getCapturedBm() {
        return capturedBm;
    }

    public void setCapturedBm(Bitmap capturedBm) {
        this.capturedBm = capturedBm;
    }
}
