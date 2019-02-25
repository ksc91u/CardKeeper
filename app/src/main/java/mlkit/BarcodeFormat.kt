package mlkit

enum class BarcodeFormat(val value:Int ) {

    /** Aztec 2D barcode format.  */
    AZTEC(4096),

    /** CODABAR 1D format.  */
    CODABAR(8),

    /** Code 39 1D format.  */
    CODE_39(2),

    /** Code 93 1D format.  */
    CODE_93(4),

    /** Code 128 1D format.  */
    CODE_128(1),

    /** Data Matrix 2D barcode format.  */
    DATA_MATRIX(16),

    /** EAN-8 1D format.  */
    EAN_8(64),

    /** EAN-13 1D format.  */
    EAN_13(32),

    /** ITF (Interleaved Two of Five) 1D format.  */
    ITF(128),

    /** MaxiCode 2D barcode format.  */
    /*MAXICODE() not supported in firebase vision,*/

    /** PDF417 format.  */
    PDF_417(2048),

    /** QR Code 2D barcode format.  */
    QR_CODE(256),

    /** RSS 14  */
    /*RSS_14, not supported in firebase vision*/

    /** RSS EXPANDED  */
    /*RSS_EXPANDED, not supported in firebase vision*/

    /** UPC-A 1D format.  */
    UPC_A(512),

    /** UPC-E 1D format.  */
    UPC_E(1024);

    /** UPC/EAN extension format. Not a stand-alone format.  */
    /*UPC_EAN_EXTENSION not supported in firebase vision*/

    companion object {
        private val map = BarcodeFormat.values().associateBy(BarcodeFormat::value)
        fun fromInt(type: Int) = map[type]
    }

    fun toZxing() : com.google.zxing.BarcodeFormat{
        return when(this){

            AZTEC -> com.google.zxing.BarcodeFormat.AZTEC
            CODABAR -> com.google.zxing.BarcodeFormat.CODABAR
            CODE_39 -> com.google.zxing.BarcodeFormat.CODE_39
            CODE_93 -> com.google.zxing.BarcodeFormat.CODE_93
            CODE_128 -> com.google.zxing.BarcodeFormat.CODE_128
            DATA_MATRIX -> com.google.zxing.BarcodeFormat.DATA_MATRIX
            EAN_8 -> com.google.zxing.BarcodeFormat.EAN_8
            EAN_13 -> com.google.zxing.BarcodeFormat.EAN_13
            ITF -> com.google.zxing.BarcodeFormat.ITF
            PDF_417 -> com.google.zxing.BarcodeFormat.PDF_417
            QR_CODE -> com.google.zxing.BarcodeFormat.QR_CODE
            UPC_A -> com.google.zxing.BarcodeFormat.UPC_A
            UPC_E -> com.google.zxing.BarcodeFormat.UPC_E
        }
    }
}

