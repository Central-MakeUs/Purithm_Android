package com.cmc.purithm.feature.filter.util

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View

object BlurUtil {
    private const val BITMAP_SCALE = 0.4f
    private const val BLUR_RADIUS = 7.5f

    /**
     * view를 blur 처리
     * */
    fun Context.blurBitmap(view : View) : Bitmap{
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val canvas = Canvas(bitmap)
            val paint = Paint().apply {
                maskFilter = BlurMaskFilter(BLUR_RADIUS, BlurMaskFilter.Blur.NORMAL)
            }
            canvas.drawBitmap(bitmap, 0f,0f, paint)
            bitmap
        } else {
            blurWithRenderScript(bitmap)
        }
    }

    /**
     * Bitmap을 blur 처리하여 return
     * targetSdk가 30아래일 경우 사용
     * */
    @TargetApi(30)
    private fun Context.blurWithRenderScript(bitmap: Bitmap) : Bitmap{
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
}