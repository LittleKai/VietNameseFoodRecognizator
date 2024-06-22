/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package com.littlekai.healthyfooddr.tflite.model;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.littlekai.healthyfooddr.dao.HealthyFoodDrApplication;

import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.ops.NormalizeOp;

import java.io.IOException;

/**
 * This TensorFlowLite classifier works with the float EfficientNet model.
 */
public class ClassifierFloatEfficientNet extends ModelClassifier {

    private static final float IMAGE_MEAN = 127.0f;
    private static final float IMAGE_STD = 128.0f;

    /**
     * Float model does not need dequantization in the post-processing. Setting mean and std as 0.0f
     * and 1.0f, repectively, to bypass the normalization.
     */
    private static final float PROBABILITY_MEAN = 0.0f;

    private static final float PROBABILITY_STD = 1.0f;
    private String modelPath, labelPath;
    private Context context;

    /**
     * Initializes a {@code ClassifierFloatMobileNet}.
     *
     * @param activity
     */
    public ClassifierFloatEfficientNet(Activity activity, Device device, int numThreads)
            throws IOException {
        super(activity, device, numThreads);
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public void setLabelPath(String labelPath) {
        this.labelPath = labelPath;
    }

    @Override
    protected String getModelPath() {
        // you can download this file from
        // see build.gradle for where to obtain this file. It should be auto
        // downloaded into assets.
//            return "flower_model.tflite";

//        HealthyFoodDrApplication healthyFoodDrApplication = (HealthyFoodDrApplication) getApplicationContext();
        String modelPath = PreferenceManager.getDefaultSharedPreferences(HealthyFoodDrApplication.getAppContext()).getString("model", "model.tflite");
        Log.d("Kai", "getModelPath: " + modelPath);
        return modelPath;
        //        return "model.tflite";
    }

    @Override
    protected String getLabelPath() {
        String label_path = "model_labels.txt";
        String modelPath = PreferenceManager.getDefaultSharedPreferences(HealthyFoodDrApplication.getAppContext()).getString("model", "model.tflite");
        if ((modelPath.equals("model1.tflite") | modelPath.equals("model_mobile_net1.tflite")))
            label_path = "model_labels1.txt";
        if ((modelPath.equals("model224.tflite") | modelPath.equals("model_mobile_net224.tflite")))
            label_path = "model_labels224.txt";
//        return "flower_labels.txt";
        Log.d("Kai", "getLabelPath: "+label_path);
        return label_path;
    }

    @Override
    protected TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }

    @Override
    protected TensorOperator getPostprocessNormalizeOp() {
        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }
}
