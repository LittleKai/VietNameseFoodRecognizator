# VietNameseFoodRecognizator

Mobile application for classifying and recognizing 20 Vietnamese dishes using an improved EfficientNet-B0 model. This project focuses on enhancing classification accuracy.

![Ingredient Detect](https://github.com/LittleKai/VietNameseFoodRecognizator/blob/main/received_1981802552271939.png)
![Vietnamese Food Classification](https://github.com/LittleKai/VietNameseFoodRecognizator/blob/main/received_674442134837302.png)

## Introduction

This application serves as an experiment for a thesis on improving the accuracy of a model trained using the TensorFlow library. The EfficientNet-B0 model is employed for this purpose. Being a baseline model with the least number of layers and levels, it allows for significant observable changes in results.

## Content

The baseline EfficientNet-B0 model is enhanced by adding four additional layers. These layers operate independently of the original model, making the enhancements applicable to all EfficientNet models from B1 to B7, as well as other networks like MobileNet and ResNet.

![Proposed Architecture](DEMO/Architecture%20Proposed.png)

*Figure 1: Proposed Architecture*

## Results Evaluation

The reliability and value of the improved EfficientNet-B0 network are evaluated using a confusion matrix (error matrix).

![Confusion Matrix Base](DEMO/Confusion%20matrix%20base.png)

*Figure 2: Confusion matrix of the original EfficientNet-B0 model*

![Confusion Matrix Proposed](DEMO/Confusion%20matrix%20proposed.png)

*Figure 3: Confusion matrix of the proposed EfficientNet-B0 model*

The comparison of the accuracy and loss of the two models is presented in the following table:

![Experimental Results](DEMO/Experimental%20results%20.png)

## Demo Images of the Application

![Demo Image 1](DEMO/DEMO1.png)

![Demo Image 2](DEMO/DEMO2.png)

![Introduction](DEMO/INTRODUCETION.png)

![Nutrition Info](DEMO/Nutrition.png)

![Nutrition Info 2](DEMO/NUTRITION2.png)
