package com.littlekai.healthyfooddr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.Utils;
import com.littlekai.healthyfooddr.adapter.FoodRecognizeAdapter;
import com.littlekai.healthyfooddr.dao.HealthyFoodDrApplication;
import com.littlekai.healthyfooddr.model.Food;
import com.littlekai.healthyfooddr.tflite.model.ModelClassifier;
import com.littlekai.healthyfooddr.utils.UtilHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodRecognizeActivity extends AppCompatActivity implements FoodRecognizeAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    FoodRecognizeAdapter adapter;
    private ModelClassifier dish_classifier;
    ArrayList<Food> foods;
    Bitmap image;
    ImageView iv_food;
    //    LinearLayout fl_contain;
    ViewGroup.LayoutParams rv_params;
    private boolean is_full_view = true;
    float default_value;
    private String TAG = "Kai";
    TextView tv_timeCost;
//    private static int firstVisibleInListview;

    //    firstVisibleInListview = yourLayoutManager.findFirstVisibleItemPosition();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recognize);

        recyclerView = findViewById(R.id.rv_recognizeFood);
//        fl_contain = findViewById(R.id.fl_contain);
        iv_food = findViewById(R.id.iv_food);
        tv_timeCost = findViewById(R.id.tv_timeCost);
        foods = new ArrayList<>();
//        image = getIntent().getParcelableExtra("image");
        HealthyFoodDrApplication healthyFoodDrApplication = (HealthyFoodDrApplication) getApplicationContext();
        image = healthyFoodDrApplication.getCapturedBm();

        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("max_result", "6"));
        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("rolation", "360"));
        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("model", "model.tflite"));
        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("thread", "1"));
        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("crop_size", "400"));

        recyclerView.post(new Runnable() {
            @Override
            public void run() {

//                default_value = fl_contain.getHeight();
                default_value = recyclerView.getHeight();
//                default_value =   fl_contain.getY();
                Log.d(TAG, "run: " + Math.round(default_value));
            }
        });

        if (image != null) {
            iv_food.setImageBitmap(image);
            showDishImage(image);
        }
        iv_food.setOnClickListener(view -> {
            float value;
            Log.d(TAG, "is_full_view: " + is_full_view);
            if (is_full_view) {
                is_full_view = false;
                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(recyclerView, View.TRANSLATION_Y, default_value / 2);
                animSet.playTogether(anim1);
                animSet.setDuration(300);
//            anim1.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
                animSet.start();
            }
//            new Handler().postDelayed(() -> {
//                Log.d(TAG, "Y: " + value);
//                rv_params =  recyclerView.getLayoutParams();
//                rv_params.height = Math.round(value);
//                recyclerView.setLayoutParams(rv_params);
////                ViewGroup.LayoutParams params = fl_contain.getLayoutParams();
////                params.height = fl_contain.getHeight() - Math.round(value);
////                fl_contain.setLayoutParams(params);
//            }, 2000);

        });
    }

    @Override
    public void onItemClick(View view, Food food) {
        if (food != null) {
            Intent intent = new Intent(this, FoodDescriptionActivity.class);
            intent.putExtra("food", food);
            startActivityForResult(intent, 202);
        }
    }

    public void showDishImage(Bitmap bmp) {
        if (bmp != null) {
            try {
                Integer max_result = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString("max_result", "6"));

                String model = PreferenceManager.getDefaultSharedPreferences(this).getString("model", "model.tflite");

                Integer thread = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString("thread", "1"));
                dish_classifier = ModelClassifier.custom_create(this, thread, max_result);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (dish_classifier != null) {
                Bitmap croppedBitmap;
                Integer rolation = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString("rolation", "360"));


                Integer crop_size = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString("crop_size", "400"));
                croppedBitmap = UtilHelper.resizeBitmap(bmp, crop_size, crop_size);
                List<ModelClassifier.Recognition> results = dish_classifier.recognizeImage(croppedBitmap, getScreenOrientation(rolation));
                if (results.size() > 0) {
                    tv_timeCost.setText(dish_classifier.getTimeCost()+" ms");
                    for (int i = 0; i < results.size(); i++) {
                        foods.add(new Food(results.get(i).getTitle(), results.get(i).getConfidence()));
                        Log.d("Kai", results.get(i).getLocation().toString());
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter = new FoodRecognizeAdapter(this, foods);
                adapter.setOnItemClickListener(this);
                recyclerView.setAdapter(adapter);

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        final int DISTANCE = 50;
                        if (!is_full_view) {
                            if (dy > DISTANCE) {
                                is_full_view = true;
                                AnimatorSet animSet = new AnimatorSet();
                                ObjectAnimator anim1 = ObjectAnimator.ofFloat(recyclerView, View.TRANSLATION_Y, 0);
                                animSet.playTogether(anim1);
                                animSet.setDuration(300);
//            anim1.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
                                animSet.start();
                            }
                        }
                    }
                });

//                Log.d("Kai", "showDishImage: "+recyclerView.get);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 202) {

                Food result = data.getParcelableExtra("result");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        } else if (resultCode == Activity.RESULT_CANCELED)
            this.finish();
    }

    protected int getScreenOrientation(int rolation) {
        if (rolation == 360)
            switch (Objects.requireNonNull(this).getWindowManager().getDefaultDisplay().getRotation()) {
                case Surface.ROTATION_270:
                    return 270;
                case Surface.ROTATION_180:
                    return 180;
                case Surface.ROTATION_90:
                    return 90;
                case Surface.ROTATION_0:
                    return 0;
                default:
                    return 0;
            }
        else return rolation;
    }


}