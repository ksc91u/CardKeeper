package mlkit;

import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;

import java.util.List;

public interface OnDetectedCallback {
    void onDetected(List<FirebaseVisionBarcode> codeList);
    void onFailure(Exception e);
}
