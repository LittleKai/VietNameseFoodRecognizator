/* Copyright 2017 The TensorFlow Authors. All Rights Reserved.

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

import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.ops.NormalizeOp;

import java.io.IOException;

/** This TensorFlow Lite classifier works with the quantized EfficientNet model. */
public class ClassifierQuantizedEfficientNet extends ModelClassifier {

  /**
   * The quantized model does not require normalization, thus set mean as 0.0f, and std as 1.0f to
   * bypass the normalization.
   */
  private static final float IMAGE_MEAN = 0.0f;

  private static final float IMAGE_STD = 1.0f;

  /** Quantized MobileNet requires additional dequantization to the output probability. */
  private static final float PROBABILITY_MEAN = 0.0f;

  private static final float PROBABILITY_STD = 255.0f;

  /**
   * Initializes a {@code ClassifierQuantizedMobileNet}.
   *
   * @param activity
   */
  public ClassifierQuantizedEfficientNet(Activity activity, Device device, int numThreads)
      throws IOException {
    super(activity, device, numThreads);
  }

  @Override
  protected String getModelPath() {
    // you can download this file from
    // see build.gradle for where to obtain this file. It should be auto
    // downloaded into assets.
//        return "flower_model.tflite";
//    return "model.tflite";
    return "efficientnet-lite0-int8.tflite";
  }

  @Override
  protected String getLabelPath() {
//        return "flower_labels.txt";
//    return "model_labels.txt";
    return "labels_without_background.txt";
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
