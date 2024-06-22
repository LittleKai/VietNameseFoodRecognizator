package com.littlekai.healthyfooddr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.littlekai.healthyfooddr.adapter.ViewPagerAdapter;
import com.littlekai.healthyfooddr.database.FoodDatabaseHelper;
import com.littlekai.healthyfooddr.fragments.DescriptionFragment;
import com.littlekai.healthyfooddr.fragments.NutritionFragment;
import com.littlekai.healthyfooddr.model.Food;
import com.littlekai.healthyfooddr.utils.UtilHelper;

public class FoodDescriptionActivity extends AppCompatActivity {
    FoodDatabaseHelper db;
    private Food food;
    private TextView tv_Nutrion, tv_Description, tv_foodName;
    private ImageView iv_avatar;
    private EditText et_noServes;
    private TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    NutritionFragment nutritionFragment;
    DescriptionFragment descriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_description);
        viewPager = (ViewPager) findViewById(R.id.pager);
        db = new FoodDatabaseHelper(this);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_foodName = findViewById(R.id.tv_foodName);

        et_noServes = findViewById(R.id.et_noServes);
        tabLayout = findViewById(R.id.tabLayout);
        food = getIntent().getParcelableExtra("food");
        food = db.getNutrion(food);
        nutritionFragment = new NutritionFragment();
        descriptionFragment = new DescriptionFragment();
        if (food != null) {
            tv_foodName.setText(food.getName() + " [" + UtilHelper.englishDishName(food.getName()) + "]");
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragments(nutritionFragment, "Nutrition");
            viewPagerAdapter.addFragments(descriptionFragment, "Introduction");
            viewPager.setOffscreenPageLimit(2);
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

            Bundle bundle = new Bundle();
            bundle.putString("des", food.getDescription());
            bundle.putString("nutri", food.getNutrion());

            descriptionFragment.setArguments(bundle);
            nutritionFragment.setArguments(bundle);
//            descriptionFragment.fragmentBegin(food);
            iv_avatar.setImageResource(UtilHelper.imageDish(food.getName()));

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
    }


    public void SaveMeal(View view) {
        Toast.makeText(this, "Saved Meal Into Your Daily Plan", Toast.LENGTH_LONG).show();
        finish();
    }
}
