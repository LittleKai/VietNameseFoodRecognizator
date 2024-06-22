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

import androidx.fragment.app.Fragment;

import com.littlekai.healthyfooddr.R;
import com.littlekai.healthyfooddr.customview.OverlayView;
import com.littlekai.healthyfooddr.env.BorderedText;
import com.littlekai.healthyfooddr.model.Food;
import com.littlekai.healthyfooddr.tflite.detect.Classifier;
import com.littlekai.healthyfooddr.tflite.detect.TFLiteObjectDetectionAPIModel;
import com.littlekai.healthyfooddr.tflite.detect.TFLiteObjectDetectionAPIModel1;

import com.littlekai.healthyfooddr.tracking.MultiBoxTracker;
import com.littlekai.healthyfooddr.utils.UtilHelper;

import java.util.List;


public class DetectObjFragment extends Fragment implements FragmentUtil {
    String TAG = "Kai";
    ImageView iv_dish;
    TextView tv_title, tv_result;
    private Classifier object_classifier;
    List<Classifier.Recognition> recognitions;
    Bitmap cropCopyBitmap = null;
    OverlayView trackingOverlay;
    private static final DetectorMode MODE = DetectorMode.TF_OD_API;
    // Minimum detection confidence to track a detection.
    private static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.2f;
    private static final boolean MAINTAIN_ASPECT = false;
    private static final Size DESIRED_PREVIEW_SIZE = new Size(640, 480);
    private static final boolean SAVE_PREVIEW_BITMAP = false;
    private static final float TEXT_SIZE_DIP = 10;
    private long lastProcessingTimeMs;
    private Bitmap rgbFrameBitmap = null;
    private Bitmap croppedBitmap = null;

    private boolean computingDetection = false;

    private long timestamp = 0;

    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;

    private MultiBoxTracker tracker;

    private BorderedText borderedText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2, container, false);
        iv_dish = rootView.findViewById(R.id.iv_dish);
        tv_title = rootView.findViewById(R.id.tv_title);
        tv_result = rootView.findViewById(R.id.tv_result);
        tv_title.setText("INGREDIENT DETECTION");
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.pho3);
        showDishImage(bmp);
        return rootView;
    }

    @Override
    public void showDishImage(Bitmap bmp) {
        if (bmp != null) {
            if (iv_dish != null)
                iv_dish.setImageBitmap(bmp);
            new Thread(() -> {
                try {
//                    object_classifier = TFLiteObjectDetectionAPIModel.create(getActivity().getAssets(),
                    object_classifier = TFLiteObjectDetectionAPIModel1.create(getActivity().getAssets(),
//                            "detect.tflite", "file:///android_asset/labels.txt",
//                            300, true);
                    "food_model_with_metadata.tflite", "file:///android_asset/labels.txt",
                            300, true);

                } catch (final Exception e) {
                    //if they aren't found, throw an error!
                    throw new RuntimeException("Error initializing classifiers!", e);
                }
                Bitmap croppedBitmap;
                croppedBitmap = UtilHelper.resizeBitmap(bmp, 300, 300);

                cropCopyBitmap = Bitmap.createBitmap(croppedBitmap);
                Bitmap mutableBitmap = cropCopyBitmap.copy(Bitmap.Config.ARGB_8888, true);

                final Canvas canvas = new Canvas(mutableBitmap);
                final Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2.0f);

                recognitions = object_classifier.recognizeImage(croppedBitmap);
                Log.d(TAG, "showDishImage: "+recognitions.size());
//                final List<Classifier.Recognition> mappedRecognitions = new LinkedList<Classifier.Recognition>();
                float minimumConfidence = MINIMUM_CONFIDENCE_TF_OD_API;

                switch (MODE) {
                    case TF_OD_API:
                        minimumConfidence = MINIMUM_CONFIDENCE_TF_OD_API;
                        break;
                }


                //if it can't classify, output a question mark

//                    tracker.trackResults(mappedRecognitions, currTimestamp);
//                    trackingOverlay.postInvalidate();

                computingDetection = false;
//                String finalResult = result;
                float finalMinimumConfidence = minimumConfidence;
                getActivity().runOnUiThread(() -> {
                    String result = "";
                    for (final Classifier.Recognition recognition : recognitions) {
                        final RectF location = recognition.getLocation();
                        if (location != null && recognition.getConfidence() >= finalMinimumConfidence) {
                            final Paint txtPaint = new Paint();
                            txtPaint.setColor(Color.RED);
                            txtPaint.setStrokeWidth(2.0f);
                            txtPaint.setTextSize(17);
                            if (recognition.getTitle() != null & recognition.getConfidence() != null)
                                canvas.drawText(recognition.getTitle() + (String.format("(%.1f%%) ", recognition.getConfidence() * 100.0f)),
                                        location.left + 3, location.top - 3, txtPaint);


                            canvas.drawRect(UtilHelper.fixLocation(location, mutableBitmap), paint);
//                            cropToFrameTransform.mapRect(location);
//                            recognition.setLocation(location);
//                            mappedRecognitions.add(recognition);
                        }
                        Log.d(TAG, "" + recognition.toString());
                        if (recognition.getTitle() != null & recognition.getConfidence() != null)
                            result = result + recognition.getTitle() + (String.format("(%.1f%%) ", recognition.getConfidence() * 100.0f)) + "\n";
                    }
                    tv_result.setText(result);
                    iv_dish.setImageBitmap(mutableBitmap);
                    // Stuff that updates the UI
                });

            }).start();

        }
    }


    @Override
    public void fragmentBegin(Food food) {

    }

    private enum DetectorMode {
        TF_OD_API;
    }
}
