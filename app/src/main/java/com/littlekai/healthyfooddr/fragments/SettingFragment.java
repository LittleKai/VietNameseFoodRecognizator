package com.littlekai.healthyfooddr.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import com.littlekai.healthyfooddr.R;
import com.littlekai.healthyfooddr.customview.OverlayView;
import com.littlekai.healthyfooddr.env.BorderedText;
import com.littlekai.healthyfooddr.model.Food;
import com.littlekai.healthyfooddr.tflite.detect.Classifier;
import com.littlekai.healthyfooddr.tracking.MultiBoxTracker;
import com.littlekai.healthyfooddr.utils.UtilHelper;

import java.util.List;



public class SettingFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

    }
}
