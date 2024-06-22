package com.littlekai.healthyfooddr.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class Food implements Parcelable {
    private String name;
    private String meal;
    private String servingSize;
    private int numberOfServing;
    private String nutrion;
    private LocalDate date;
    private String description;
    private float confidence;

    public Food(String name, String meal, String servingSize, int numberOfServing, String nutrion, LocalDate date) {
        this.name = name;
        this.meal = meal;
        this.servingSize = servingSize;
        this.numberOfServing = numberOfServing;
        this.nutrion = nutrion;
        this.date = date;

    }
    public Food(String name,float confidence) {
        this.name = name;
        this.confidence = confidence;
    }


    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public int getNumberOfServing() {
        return numberOfServing;
    }

    public void setNumberOfServing(int numberOfServing) {
        this.numberOfServing = numberOfServing;
    }

    public String getNutrion() {
        return nutrion;
    }

    public void setNutrion(String nutrion) {
        this.nutrion = nutrion;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Creator<Food> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    protected Food(Parcel in) {
        name = in.readString();
        servingSize = in.readString();
        description = in.readString();
        nutrion = in.readString();
        meal = in.readString();
        numberOfServing =  in.readInt();
        confidence = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(servingSize);
        parcel.writeString(description);
        parcel.writeString(nutrion);
        parcel.writeString(meal);
        parcel.writeInt(numberOfServing);
        parcel.writeFloat(confidence);
    }
}
