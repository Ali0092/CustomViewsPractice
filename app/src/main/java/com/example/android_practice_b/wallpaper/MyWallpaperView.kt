package com.example.android_practice_b.wallpaper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyWallpaperView(context: Context,attributeSet: AttributeSet?):View(context,attributeSet) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 50f
        canvas?.drawText("Hello, world!", 100f, 100f, paint)
    }
}