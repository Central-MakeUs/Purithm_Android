package com.cmc.purithm.feature.filter.util

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import androidx.annotation.RequiresApi
import org.junit.runners.AllTests

object BlurUtil {
    private const val BITMAP_SCALE = 0.4f
    private const val BLUR_RADIUS = 7.5f

    /**
     * Bitmap을 blur 처리하여 return
     * targetSdk가 30아래일 경우 사용
     * */
    @TargetApi(30)
    fun Context.blurWithRenderScript(bitmap: Bitmap) : Bitmap{
        val width = Math.round(bitmap.width * BITMAP_SCALE)
        val height = Math.round(bitmap.height * BITMAP_SCALE)

        val inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(this)
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8(rs))
        val input = Allocation.createFromBitmap(rs, inputBitmap)
        val output = Allocation.createFromBitmap(rs, outputBitmap)

        blurScript.setRadius(BLUR_RADIUS)
        blurScript.setInput(input)
        blurScript.forEach(output)
        output.copyTo(outputBitmap)

        return outputBitmap
    }

    /**
     * view를 blur 처리
     * */
    @RequiresApi(Build.VERSION_CODES.S)
    fun Context.blur(view : View){
        val blurRenderEffect = RenderEffect.createBlurEffect(BLUR_RADIUS, BLUR_RADIUS, Shader.TileMode.MIRROR)
        view.setRenderEffect(blurRenderEffect)
    }
}