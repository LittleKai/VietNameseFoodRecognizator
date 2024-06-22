package com.littlekai.healthyfooddr.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.media.Image;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.littlekai.healthyfooddr.R;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class UtilHelper {
    private static final String TAG = "Kai";

    private void imageToBitmap(Image image) {
//    Image image = reader.acquireLatestImage();
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }

            return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, false);
        }
        return bitmap;
    }

    public static RectF fixLocation(RectF location, Bitmap bitmap) {
        float x1 = location.left;
        float y1 = location.top;
        float x2 = location.right;
        float y2 = location.bottom;
        if (x1 < 0) x1 = 1;
        if (y1 < 0) y1 = 1;
        if (x2 >= bitmap.getWidth())
            x2 = (float) bitmap.getWidth() - 1;
        if (y2 >= bitmap.getHeight())
            y2 = (float) bitmap.getHeight() - 1;
//        Log.d(TAG, "fixLocation: "+ new RectF(x1, y1, x2, y2));
        return new RectF(x1, y1, x2, y2);

    }

    public static boolean checkAndRequestPermissions(Context context) {
        int writePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 123);
            return false;
        }
        return true;
    }

    public static String englishDishName(String name) {
        String result = "";
        switch (name) {
            case "banh bao":
                result = "Buns";
                break;
            case "banh cuon":
                result = "Rice Flourm";
                break;
            case "banh mi":
                result = "Bread";
                break;
            case "banh xeo":
                result = "Vietnamese Pancake";
                break;
            case "bo ne":
                result = "Beef Steak";
                break;
            case "cha gio":
                result = "Spring rolls";
                break;
            case "pho":
                result = "Pho Noodle";
                break;
            case "bun bo":
                result = "Beef Noodle";
                break;
            case "cha ca":
                result = "Vietnamese Fish Cake";
                break;
            case "cha lua":
                result = "Vietnamese Sausage";
                break;
            case "chao":
                result = "Rice Porridge";
                break;
            case "com chien":
                result = "Fried Rice";
                break;
            case "goi":
                result = "Sweet and Sour Salad";
                break;
            case "lau":
                result = "Hotspot";
                break;
            case "che":
                result = "Sweet Soup";
                break;
            case "sup":
                result = "Soup";
                break;
            case "dau hu":
                result = "Tofu";
                break;
            case "bo la lot":
                result = "Beef With Betel Leaf";
                break;
            case "thit kho tau":
                result = "Caramelized Pork and Eggs";
                break;
            case "canh chua":
                result = "Vietnamese Sour Soup";
                break;
            case "com":
                result = "Vietnamese broken rice";
                break;
            case "com tam":
                result = "Vietnamese broken rice";
                break;
            case "suon xao chua ngot":
                result = "Sweet and Sour Pork";
                break;
            case "ca kho":
                result = "Fish Stew";
                break;
            default:
                result = name;
        }
        return result;
    }

    public static int imageDish(String name) {
        int result;
        switch (name) {
            case "banh bao":
                result = R.drawable.banh_bao;
                break;
            case "banh cuon":
                result = R.drawable.banh_cuon;
                break;
            case "banh mi":
                result = R.drawable.banh_mi;
                break;
            case "banh xeo":
                result = R.drawable.banh_xeo;
                break;
            case "bo ne":
                result = R.drawable.bo_ne;
                break;
            case "pho":
                result = R.drawable.pho3;
                break;
            case "bun bo":
                result = R.drawable.bun_bo;
                break;
            case "cha ca":
                result = R.drawable.cha_ca;
                break;
            case "cha lua":
                result = R.drawable.cha_lua;
                break;
            case "cha gio":
                result = R.drawable.cha_gio;
                break;
            case "chao":
                result = R.drawable.chao;
                break;
            case "com":
                result = R.drawable.com_tam;
                break;
            case "com chien":
                result = R.drawable.com_chien;
                break;
            case "goi":
                result = R.drawable.goi;
                break;
            case "lau":
                result = R.drawable.lau1;
                break;
            case "che":
                result = R.drawable.che;
                break;
            case "sup":
                result = R.drawable.sup1;
                break;
            case "dau hu":
                result = R.drawable.dau_hu;
                break;
            case "bo la lot":
                result = R.drawable.bo_la_lot;
                break;
            case "thit kho tau":
                result = R.drawable.thit_kho_tau;
                break;
            case "canh chua":
                result = R.drawable.canh_chua;
                break;
            case "com tam":
                result = R.drawable.com_tam;
                break;
            case "suon xao chua ngot":
                result = R.drawable.thit_xao_chua_ngot;
                break;
            case "ca kho":
                result = R.drawable.ca_kho;
                break;
            default:
                result = R.drawable.error_image;
        }
        return result;
    }

    
}
