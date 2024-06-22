package com.littlekai.healthyfooddr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toolbar;


import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.littlekai.healthyfooddr.adapter.ViewPagerAdapter;
import com.littlekai.healthyfooddr.dao.HealthyFoodDrApplication;
import com.littlekai.healthyfooddr.fragments.DailyMealFragment;
import com.littlekai.healthyfooddr.fragments.DetectObjFragment;
import com.littlekai.healthyfooddr.fragments.SettingFragment;
//import com.littlekai.healthyfooddr.model.Classification;
import com.littlekai.healthyfooddr.model.Food;
import com.littlekai.healthyfooddr.tflite.classification.ImageClassifier;
import com.littlekai.healthyfooddr.tflite.detect.Classifier;
import com.littlekai.healthyfooddr.tflite.model.ModelClassifier;
import com.littlekai.healthyfooddr.tflite.model.ModelClassifier.Device;
import com.littlekai.healthyfooddr.tflite.model.ModelClassifier.Model;
//import com.littlekai.healthyfooddr.model.TFLiteObjectDetectionAPIModel;
//import com.littlekai.healthyfooddr.models.TensorFlowClassifier3;
//import com.littlekai.healthyfooddr.models.TensorFlowImageClassifier;
//import com.littlekai.healthyfooddr.models.TensorFlowClassifier3;

import com.littlekai.healthyfooddr.utils.UtilHelper;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import static android.Manifest.permission_group.CAMERA;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;
    //Fragments
    DailyMealFragment dailyMealFragment;
    SettingFragment settingFragment;
    DetectObjFragment detectObjFragment;
    SpaceNavigationView spaceNavigationView;
    private String TAG = "Kai";
    private List<Classifier> mClassifiers = new ArrayList<>();
    private Classifier object_classifier;
    private ModelClassifier dish_classifier;
    private List<ImageClassifier> mImgClassifiers = new ArrayList<>();
    Bitmap myBitmap;
    Uri picUri;


    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private int INPUT_WIDTH = 300;
    private int INPUT_HEIGHT = 300;
    private int IMAGE_MEAN = 128;
    private float IMAGE_STD = 128f;
    private String INPUT_NAME = "Mul";
    private String OUTPUT_NAME = "final_result";
    private String MODEL = "model.tflite";
    private String LABEL = "labels.txt";
    private String MODEL_FILE = "file:///android_asset/pho_tf.pb";
    private String LABEL_FILE = "file:///android_asset/labels.txt";
    private String DISH_LABEL_FILE = "file:///android_asset/model_labels.txt";
    List<Classifier.Recognition> recognitions;
    int sensorOrientation = 0;
    Uri outputFileUri;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int REQUEST_DISPATCH_GALLERY = 101;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        setupViewPager(viewPager);
        UtilHelper.checkAndRequestPermissions(this);
        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("max_result", "6"));
        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("rolation", "90"));

        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("model", "model.tflite"));

        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("thread", "1"));

        Log.d(TAG, "onCreate: " + PreferenceManager.getDefaultSharedPreferences(this).getString("crop_size", "400"));


//        checkPermission();
//        loadCamera();
    }

    private void setupViewPager(final ViewPager viewPager) {
        spaceNavigationView.addSpaceItem(new SpaceItem("MEAL", R.drawable.icon_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("SETTINGS", R.drawable.ic_main_settings));
        recognitions = new ArrayList<>();
        //Initializing the spaceNavigationView
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                selectImage();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        detectObjFragment = new DetectObjFragment();
        settingFragment = new SettingFragment();
        adapter.addFragments(detectObjFragment);
        adapter.addFragments(settingFragment);
        viewPager.setAdapter(adapter);
        Log.d(TAG, "sensorOrientation: " + getScreenOrientation());
    }


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    String imageFilePath;

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


    private String pictureImagePath = "";


    private boolean checkPermission(int request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (request == CAMERA_PERMISSION_CODE)
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                else return true;
            if (request == STORAGE_PERMISSION_CODE)
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, STORAGE_PERMISSION_CODE);
                else return true;
        }
        return false;
    }

    protected int getScreenOrientation() {
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_270:
                return 270;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_90:
                return 90;
            default:
                return 0;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
    }

    private static final int TAKE_PICTURE = 1;
    private Uri mImageUri;
    private int PICK_FROM_CAMERA = 1;

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.littlekai.healthyfooddr.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
//                startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    Uri imageUri;

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_DISPATCH_GALLERY);
    }

    private void selectImage() {
        final CharSequence[] options = {"Test Obj Detection","Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Option!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
//                    openBackCamera();
                    if (checkPermission(CAMERA_PERMISSION_CODE))
                        if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("test_input", false))
                            test(true);
                        else
                            openCameraIntent();
                } else if (options[item].equals("Choose from Gallery")) {
                    if (checkPermission(STORAGE_PERMISSION_CODE))
                        dispatchGalleryIntent();
                }
                else if (options[item].equals("Test Obj Detection")) {
                    if (checkPermission(CAMERA_PERMISSION_CODE))
                        if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("test_input", false))
                            test(false);
//                        else
//                            openCameraIntent();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap[] bitmap = new Bitmap[1];
        if (resultCode == RESULT_OK) {

            if (requestCode == 201) {
                Food result = data.getParcelableExtra("result");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }

            if (requestCode == REQUEST_CAPTURE_IMAGE) {
//                if (data != null)
//                    if (data.getExtras() != null)
//                        bitmap[0] = (Bitmap) data.getExtras().get("data");
                try {
                    bitmap[0] = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), imageUri);
//                        imageurl = getRealPathFromURI(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_DISPATCH_GALLERY) {
                Uri selectedImage = data.getData();

                // method 1
                try {
                    bitmap[0] = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                    dailyMealFragment.showDishImage(bitmap[0]);
//                    settingFragment.showDishImage(bitmap[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bitmap[0] != null) {
                Log.d(TAG, bitmap[0].getHeight() + "," + bitmap[0].getWidth());
                Intent intent = new Intent(this, FoodRecognizeActivity.class);
//                intent.putExtra("image", bitmap[0]);
                HealthyFoodDrApplication healthyFoodDrApplication = (HealthyFoodDrApplication) getApplicationContext();
                healthyFoodDrApplication.setCapturedBm(bitmap[0]);
                startActivityForResult(intent, 201);
                //                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        }
    }

    void test(boolean task) {

        int[] scr = {R.drawable.banhbao1, R.drawable.banhcuon1, R.drawable.banhmi1, R.drawable.banhxeo1, R.drawable.bone1, //5
                R.drawable.bunbo1, R.drawable.chaca1, R.drawable.chalua1, R.drawable.chao1, R.drawable.rice1,
                R.drawable.comchien1, R.drawable.goi1, R.drawable.pho1, R.drawable.sup1, R.drawable.pho2,
                R.drawable.pho4, R.drawable.pho5, R.drawable.pho6, R.drawable.goi2, R.drawable.dau_hu6, R.drawable.cha_gio2,
                R.drawable.dauhu1, R.drawable.dauhu2, R.drawable.bo_la_lot_1, R.drawable.ca_kho_1, R.drawable.canh_chua_1, R.drawable.canh_chua3,
                R.drawable.thit_chua_ngot1, R.drawable.thit_kho_tau1, R.drawable.suon_xao1};
        Bitmap[] bitmap = new Bitmap[1];
        bitmap[0] = BitmapFactory.decodeResource(this.getResources(), scr[ThreadLocalRandom.current().nextInt(0, scr.length)]);

        if (bitmap[0] != null)
            if (task)
            {
            HealthyFoodDrApplication healthyFoodDrApplication = (HealthyFoodDrApplication) getApplicationContext();
            healthyFoodDrApplication.setCapturedBm(bitmap[0]);
            Intent intent = new Intent(this, FoodRecognizeActivity.class);
//        intent.putExtra("image", croppedBitmap);
            startActivityForResult(intent, 201);
            //                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
        else {
                detectObjFragment.showDishImage(bitmap[0]);
        }
    }

    int CAMERA_PERMISSION_CODE = 101;
    int STORAGE_PERMISSION_CODE = 201;

}

