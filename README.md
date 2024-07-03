# VietNameseFoodRecognizator

Mobile application for classifying and recognizing 20 Vietnamese dishes. Utilizing the model architecture built from EfficientNet-B0, I experiment with improving classification accuracy.

![INGREDIENT DETECT](https://github.com/LittleKai/VietNameseFoodRecognizator/blob/main/received_1981802552271939.png)
![VIETNAMESE FOOD CLASSFICATION](https://github.com/LittleKai/VietNameseFoodRecognizator/blob/main/received_674442134837302.png)
1. Introduction:

This application serves as an experiment for a thesis on improving the accuracy of a model trained using the TensorFlow library. Here, I employ the EfficientNet-B0 model for experimentation. Since the EfficientNet-B0 network is a baseline model with the least number of layers and levels, it will yield the most noticeable change in results.

2. Content:

The baseline EfficientNet B0 model is enhanced by adding four more layers, as detailed in the following table:

(DEMO/Architecture%20Proposed.png)

Figure 1: Table

These four added layers operate entirely independently of the original baseline model, making them applicable to all EfficientNet models from B1 to B7, as well as models belonging to other networks like MobileNet and ResNet.

3. Results Evaluation:

The reliability and value of the improved EfficientNet network are evaluated using a confusion matrix (error matrix).
(DEMO/Confusion%20matrix%20base.png)

Figure 2: Confusion matrix of the original EfficientNet-B0 model

(DEMO/Confusion%20matrix%20proposed.png)

Figure 3: Confusion matrix of the proposed EfficientNet-B0 model

The comparison of the accuracy and loss of the two models is presented in the following table:

(DEMO/Experimental%20results%20.png)

4. Demo Images of the Application:
   
(DEMO/DEMO1.png)

(DEMO/DEMO2.png)

(DEMO/INTRODUCETION.png)

(DEMO/Nutrition.png)

(DEMO/NUTRITION2.png)

