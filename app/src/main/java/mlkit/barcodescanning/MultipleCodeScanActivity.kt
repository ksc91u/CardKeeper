package mlkit.barcodescanning

import android.app.Activity
import android.content.Intent
import android.widget.TextView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.TWSuperMarketCode
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.common.Barcode.FORMAT_CODE_39
import com.king.mlkit.vision.barcode.QRCodeCameraScanActivity
import com.king.mlkit.vision.barcode.analyze.BarcodeScanningAnalyzer
import com.king.mlkit.vision.camera.AnalyzeResult
import com.king.mlkit.vision.camera.analyze.Analyzer
import com.king.mlkit.vision.camera.config.ResolutionCameraConfig
import mlkit.BarCode
import mlkit.BarcodeFormat

class MultipleCodeScanActivity: QRCodeCameraScanActivity() {
    private val code: TWSuperMarketCode = TWSuperMarketCode()

    private val requireCheckSum: Boolean by lazy {
        intent.getBooleanExtra("CHECKSUM", true)
    }

    override fun initCameraScan() {
        super.initCameraScan()
        cameraScan.setPlayBeep(false)
            .setVibrate(false)
//            .setCameraConfig(AspectRatioCameraConfig(this))
            .setCameraConfig(
                ResolutionCameraConfig(
                    this,
                    ResolutionCameraConfig.IMAGE_QUALITY_1080P
                )
            )
    }

    override fun createAnalyzer(): Analyzer<MutableList<Barcode>>? {
        return BarcodeScanningAnalyzer(FORMAT_CODE_39, *IntArray(0))
    }

    override fun onScanResultCallback(p0: AnalyzeResult<MutableList<Barcode>>) {
        val textView = this@MultipleCodeScanActivity.findViewById<TextView>(R.id.codeView)
        code.addCodes(p0.result.map {
            BarCode(
                it.rawValue ?: "",
                it.displayValue ?: "",
                BarcodeFormat.fromInt(it.format) ?: BarcodeFormat.CODE_39
            )
        })
        textView.text = code.code16?.displayValue + "\n" + code.code9?.displayValue + "\n" +
            code.code15?.firstOrNull()?.displayValue + "\n" + code.code15?.lastOrNull()?.displayValue
        if (code.isComplete(requireCheckSum)) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("TWCODE", code)
            })
            finish()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_multiple_code_scan
    }
}