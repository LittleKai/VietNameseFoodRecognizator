package com.littlekai.healthyfooddr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import com.littlekai.healthyfooddr.model.Food;

import java.util.ArrayList;

public class FoodDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food_recognition.db";
    private static final String TAG = "Kai";

    private static final String TABLE_NUTRION = "nutrion";
    private static final String TABLE_MEAL = "meals";

    //TABLE_NUTRION COLUMNS
    private static final String TABLE_NUTRION_Dish = "Dish";
    private static final String TABLE_NUTRION_Name = "Name";
    private static final String TABLE_NUTRION_Represent = "Represent";
    private static final String TABLE_NUTRION_Description = "Description";
    private static final String TABLE_NUTRION_Size = "Size";
    private static final String TABLE_NUTRION_Calories = "Calories";
    private static final String TABLE_NUTRION_Carbs = "Carbs";
    private static final String TABLE_NUTRION_Dietary_Fiber = "Dietary_Fiber";
    private static final String TABLE_NUTRION_Sugar = "Sugar";
    private static final String TABLE_NUTRION_Fat = "Fat";
    private static final String TABLE_NUTRION_Saturated = "Saturated";
    private static final String TABLE_NUTRION_Polyunsaturated = "Polyunsaturated";
    private static final String TABLE_NUTRION_Monounsaturated = "Monounsaturated";
    private static final String TABLE_NUTRION_Trans = "Trans";
    private static final String TABLE_NUTRION_Protein = "Protein";
    private static final String TABLE_NUTRION_Sodium = "Sodium";
    private static final String TABLE_NUTRION_Potassium = "Potassium";
    private static final String TABLE_NUTRION_Cholesterol = "Cholesterol";
    private static final String TABLE_NUTRION_Vitamin_A = "Vitamin_A";
    private static final String TABLE_NUTRION_Vitamin_C = "Vitamin_C";
    private static final String TABLE_NUTRION_Calcium = "Calcium";
    private static final String TABLE_NUTRION_Iron = "Iron";

    //TABLE_MEAL COLUMNS
    private static final String TABLE_MEAL_name = "name";
    private static final String TABLE_MEAL_sv_size = "sv_size";
    private static final String TABLE_MEAL_number_sv = "number_sv";
    private static final String TABLE_MEAL_nutrion = "nutrion";
    private static final String TABLE_MEAL_date = "date";
    private static final String TABLE_MEAL_meal = "meal";

    public FoodDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public FoodDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Food getNutrion(Food food) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NUTRION + " WHERE " + TABLE_NUTRION_Dish + "= '" + food.getName() + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String description = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Description));
                String Size = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Size));
                String Calories = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Calories));
                String Carbs = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Carbs));
                String Dietary_Fiber = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Dietary_Fiber));
                String Sugar = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Sugar));
                String Fat = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Fat));
                String Saturated = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Saturated));
                String Polyunsaturated = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Polyunsaturated));
                String Monounsaturated = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Monounsaturated));
                String Trans = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Trans));
                String Protein = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Protein));
                String Sodium = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Sodium));
                String Potassium = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Potassium));
                String Cholesterol = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Cholesterol));
                String Vitamin_A = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Vitamin_A));
                String Vitamin_C = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Vitamin_C));
                String Calcium = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Calcium));
                String Iron = cursor.getString(cursor.getColumnIndex(TABLE_NUTRION_Iron));

                food.setNutrion(Calories + ',' + Carbs + ',' + Dietary_Fiber + ',' + Sugar + ',' + Fat + ',' + Saturated + ','
                        + Polyunsaturated + ',' + Monounsaturated + ',' + Trans + ',' + Protein + ',' + Sodium + ',' +
                        Potassium + ',' + Cholesterol + ',' + Vitamin_A + ',' + Vitamin_C + ',' + Calcium + ',' + Iron);
                food.setServingSize(Size);
                food.setDescription(description);

                cursor.close();
                return food;
            }
        }
        cursor.close();
        return null;
    }
}
