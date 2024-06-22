package com.littlekai.healthyfooddr.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.littlekai.healthyfooddr.R;
import com.littlekai.healthyfooddr.model.Food;

public class NutritionFragment extends Fragment implements FragmentUtil {
    String TAG = "Kai";
    EditText et_noServes;
    private TextView tv_size1, tv_size2,
            tv_calo1, tv_fat1, tv_cab1, tv_protein1,
            tv_calo, tv_totalFat, tv_saturFat, tv_transFat, tv_polyFat, tv_choles, tv_sodium, tv_potassium,
            tv_totalCarb, tv_dietFiber, tv_sugar, tv_protein, tv_vitaminA, tv_vitaminC, tv_calcium, tv_iron;
    private ImageView iv_increaseServe, iv_decreaseServe;
    private String[] data;
    private int serve_size = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nutrition, container, false);
        tv_size1 = rootView.findViewById(R.id.tv_size1);
        tv_size2 = rootView.findViewById(R.id.tv_size2);
        tv_calo1 = rootView.findViewById(R.id.tv_calo1);
        tv_fat1 = rootView.findViewById(R.id.tv_fat1);
        tv_cab1 = rootView.findViewById(R.id.tv_cab1);
        tv_protein1 = rootView.findViewById(R.id.tv_protein1);
        tv_calo = rootView.findViewById(R.id.tv_calo);
        tv_totalFat = rootView.findViewById(R.id.tv_totalFat);
        tv_saturFat = rootView.findViewById(R.id.tv_saturFat);
        tv_transFat = rootView.findViewById(R.id.tv_transFat);
        tv_polyFat = rootView.findViewById(R.id.tv_polyFat);
        tv_choles = rootView.findViewById(R.id.tv_choles);
        tv_sodium = rootView.findViewById(R.id.tv_sodium);
        tv_potassium = rootView.findViewById(R.id.tv_potassium);
        tv_totalCarb = rootView.findViewById(R.id.tv_totalCarb);
        tv_dietFiber = rootView.findViewById(R.id.tv_dietFiber);
        tv_dietFiber = rootView.findViewById(R.id.tv_dietFiber);
        tv_sugar = rootView.findViewById(R.id.tv_sugar);
        tv_protein = rootView.findViewById(R.id.tv_protein);
        tv_vitaminA = rootView.findViewById(R.id.tv_vitaminA);
        tv_vitaminC = rootView.findViewById(R.id.tv_vitaminC);
        tv_calcium = rootView.findViewById(R.id.tv_calcium);
        tv_iron = rootView.findViewById(R.id.tv_iron);

        et_noServes = rootView.findViewById(R.id.et_noServes);
        iv_increaseServe = rootView.findViewById(R.id.iv_increaseServe);
        iv_increaseServe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseInteger();
            }
        });
        iv_decreaseServe = rootView.findViewById(R.id.iv_decreaseServe);
        iv_decreaseServe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseInteger();
            }
        });
        tv_size1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_size1.setBackgroundResource(R.color.orange);
//                tv_size1.setTextColor(0xFFFFFF);
                tv_size1.setTextColor(Color.WHITE);
//                tv_size1.setText("1 small bowl");
                tv_size2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.blue_border));
//                tv_size2.setTextColor(0x000000);
                tv_size2.setTextColor(Color.BLACK);
//                tv_size2.setText("1 big bowl");
                serve_size = 2;
                updateData(data, Integer.parseInt(et_noServes.getText().toString()));
            }
        });
        tv_size2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_size2.setBackgroundResource(R.color.orange);
//                tv_size2.setTextColor(0xFFFFFF);
                tv_size2.setTextColor(Color.WHITE);
//                tv_size1.setText("1 small bowl");
                tv_size1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.blue_border));
                tv_size1.setTextColor(Color.BLACK);
//                tv_size1.setTextColor(0x000000);
//                tv_size2.setText("1 big bowl");
                serve_size = 3;
                updateData(data, Integer.parseInt(et_noServes.getText().toString()));
            }
        });
        assert getArguments() != null;
        String strtext = getArguments().getString("nutri");
        data = new String[0];
        if (strtext != null)
            data = strtext.split(",");
        updateData(data, 1);

        return rootView;
    }

    void updateData(String[] data, int sv_num) {
        String dt;
        if (data.length > 0)
            for (int i = 0; i < data.length; i++) {
                dt = data[i];
                if (!data[i].equals("--"))
                    dt = (Integer.parseInt(data[i]) * sv_num * serve_size / 2) + "";
                switch (i) {
                    case 0:
                        tv_calo.setText(dt + "");
                        tv_calo1.setText((Html.fromHtml("Calories<br><b>" + dt + "</b>")));
                        break;
                    case 1:
                        tv_totalCarb.setText((dt + " g"));
                        tv_cab1.setText((Html.fromHtml("Total Carb<br><b>" + dt + " g</b>")));
                        break;
                    case 2:
                        tv_dietFiber.setText((dt + " g"));
                        break;
                    case 3:
                        tv_totalFat.setText((dt + " g"));
                        tv_fat1.setText((Html.fromHtml("Total Fat<br><b>" + dt + " g</b>")));

                        break;
                    case 4:
                        tv_saturFat.setText((dt + " g"));
                        break;
                    case 5:
                        tv_sugar.setText((dt + " g"));
                        break;
                    case 6:
                        tv_polyFat.setText((dt + " g"));
                        break;
                    case 7:
//        tv_mo.setText((Integer.parseInt(data[i])*sv_num));
                        break;
                    case 8:
                        tv_transFat.setText((dt + " g"));
                        break;
                    case 9:

                        tv_protein.setText((dt + " g"));
                        tv_protein1.setText((Html.fromHtml("Protein<br><b>" + dt + " g</b>")));
                        break;
                    case 10:
                        tv_sodium.setText((dt + " mg"));
                        break;
                    case 11:
                        tv_potassium.setText((dt + " mg"));
                        break;
                    case 12:
                        tv_choles.setText((dt + " mg"));
                        break;
                    case 13:
                        tv_vitaminA.setText((data[i] + " %"));
                        break;
                    case 14:
                        tv_vitaminC.setText((data[i] + " %"));
                        break;
                    case 15:
                        tv_calcium.setText((data[i] + " %"));
                        break;
                    case 16:
                        tv_iron.setText((data[i] + " %"));
                        break;
                }
            }
    }


    void increaseInteger() {
        int serv = Integer.parseInt(et_noServes.getText().toString()) + 1;
        et_noServes.setText(serv + "");
        Log.d(TAG, "increaseInteger: " + serv);
        updateData(data, serv);

    }

    void decreaseInteger() {
        int serv = Integer.parseInt(et_noServes.getText().toString()) - 1;
        if (serv < 0) serv = 0;
        et_noServes.setText(serv + "");
        Log.d(TAG, "increaseInteger: " + serv);
        updateData(data, serv);

    }

    @Override
    public void showDishImage(Bitmap bitmap) {

    }

    @Override
    public void fragmentBegin(Food food) {

    }
}
