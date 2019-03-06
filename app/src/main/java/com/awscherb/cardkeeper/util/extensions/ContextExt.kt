package com.awscherb.cardkeeper.util.extensions

import android.content.Context

fun Context.dpToPixel(dp: Float): Float {
    return dp * getDensity()
}

fun Context.pixelToDp(pixel: Float) : Float {
    return pixel / getDensity()
}

fun Context.getDensity(): Float {
    return this.resources.displayMetrics.density
}