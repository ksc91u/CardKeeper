// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package mlkit.barcodescanning;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.IOException;
import java.util.List;

import mlkit.FrameMetadata;
import mlkit.GraphicOverlay;
import mlkit.OnDetectedCallback;
import mlkit.VisionProcessorBase;

/** Barcode Detector Demo. */
public class BarcodeScanningProcessor extends VisionProcessorBase<List<FirebaseVisionBarcode>> {

  private static final String TAG = "BarcodeScanningProcessor";

  private final FirebaseVisionBarcodeDetector detector;

  @Nullable
  private OnDetectedCallback mCallback;

  public BarcodeScanningProcessor(@Nullable OnDetectedCallback callback) {
    // Note that if you know which format of barcode your app is dealing with, detection will be
    // faster to specify the supported barcode formats one by one, e.g.
    FirebaseVisionBarcodeDetectorOptions options = new FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_CODE_39)
            .build();
    detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);
    mCallback = callback;
  }

  @Override
  public void stop() {
    try {
      detector.close();
    } catch (IOException e) {
      Log.e(TAG, "Exception thrown while trying to close Barcode Detector: " + e);
    }
  }

  @Override
  protected Task<List<FirebaseVisionBarcode>> detectInImage(FirebaseVisionImage image) {
    return detector.detectInImage(image);
  }

  @Override
  protected void onSuccess(
      @NonNull List<FirebaseVisionBarcode> barcodes,
      @NonNull FrameMetadata frameMetadata,
      @NonNull GraphicOverlay graphicOverlay) {

    if(mCallback != null){
      mCallback.onDetected(barcodes);
    }

    graphicOverlay.clear();
    for (int i = 0; i < barcodes.size(); ++i) {
      FirebaseVisionBarcode barcode = barcodes.get(i);
      BarcodeGraphic barcodeGraphic = new BarcodeGraphic(graphicOverlay, barcode);
      graphicOverlay.add(barcodeGraphic);
    }
  }

  @Override
  protected void onFailure(@NonNull Exception e) {
    if(mCallback != null){
      mCallback.onFailure(e);
    }
    Log.e(TAG, "Barcode detection failed " + e);
  }
}
