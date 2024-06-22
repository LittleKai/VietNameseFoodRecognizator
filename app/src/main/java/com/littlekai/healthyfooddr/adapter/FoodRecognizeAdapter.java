package com.littlekai.healthyfooddr.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.littlekai.healthyfooddr.R;
import com.littlekai.healthyfooddr.model.Food;
import com.littlekai.healthyfooddr.utils.BigCircleTransform;
import com.littlekai.healthyfooddr.utils.UtilHelper;

import org.tensorflow.lite.Interpreter;

import java.util.ArrayList;
import java.util.List;

public class FoodRecognizeAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<Food> items;
    private OnItemClickListener onItemClickListener;
    private Context context;
    //    private BitmapDrawable cvHoldIcon, cvErrIcon;
    private Bitmap roundedIcon, roundedIcon1;

    public FoodRecognizeAdapter(Context c, List<Food> items) {
        context = c;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_recognize, parent, false);
        v.setOnClickListener(this);
        vh = new FoodViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FoodViewHolder) {
            final Food food = items.get(position);
            ((FoodViewHolder) holder).tv_foodName.setText(food.getName());
//            ((FoodViewHolder) holder).tv_Description.setText(Html.fromHtml("<b><font color = #795548>" + food.getDescription() + "</b></font>"));

//            ((FoodViewHolder) holder).image.setImageBitmap(null);

            Glide.with(((FoodViewHolder) holder).image.getContext()).load(UtilHelper.imageDish(food.getName()))
                    .transform(new BigCircleTransform(context)).into(((FoodViewHolder) holder).image);

//            ((FoodViewHolder) holder).tv_Nutrition.setText(Html.fromHtml("Giới Thiệu: <font color = #000000>" + food.getNutrion() + "</font>"));
            ((FoodViewHolder) holder).tv_Description.setText(UtilHelper.englishDishName(food.getName()));
            ((FoodViewHolder) holder).tv_RecognizeRate.setText(String.format("%.2f", (food.getConfidence() * 100)) + " %");
            holder.itemView.setTag(food);
        }

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    private static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tv_foodName, tv_Description, tv_Nutrition, tv_RecognizeRate;
        ImageView image;
        CardView container;

        FoodViewHolder(View v) {
            super(v);
            container = (CardView) v.findViewById(R.id.cardview_container);
            tv_foodName = (TextView) v.findViewById(R.id.tv_foodName);
            tv_Description = (TextView) v.findViewById(R.id.tv_Description);
            tv_Nutrition = (TextView) v.findViewById(R.id.tv_Nutrition);
            tv_RecognizeRate = (TextView) v.findViewById(R.id.tv_RecognizeRate);
            image = (ImageView) v.findViewById(R.id.iv_foodIcon);

        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void onClick(final View v) {
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (Food) v.getTag());
                }
            }, 200);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, Food food);

    }
}
