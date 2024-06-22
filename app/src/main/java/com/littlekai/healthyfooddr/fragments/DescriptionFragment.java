package com.littlekai.healthyfooddr.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.littlekai.healthyfooddr.R;
import com.littlekai.healthyfooddr.model.Food;

public class DescriptionFragment extends Fragment implements FragmentUtil {
    String TAG = "Kai";
    TextView tv_description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description, container, false);

        assert getArguments() != null;
        String strtext = getArguments().getString("des");
        tv_description = rootView.findViewById(R.id.tv_Description);
        tv_description.setText(Html.fromHtml(strtext));
        return rootView;
    }

    @Override
    public void showDishImage(Bitmap bitmap) {

    }

    @Override
    public void fragmentBegin(Food food) {
        Log.d(TAG, "fragmentBegin: " + food.getDescription());
        tv_description.setText(Html.fromHtml(food.getDescription()));

    }
}
