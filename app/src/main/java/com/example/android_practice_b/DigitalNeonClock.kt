package com.example.android_practice_b

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class DigitalNeonClock(context: Context,attributeSet: AttributeSet):View(context,attributeSet) {

    var mWidth = 0f
    var mHeight = 0f
    var mCenterX = 0f
    var mCenterY = 0f

    fun initializer(){
        mHeight = height.toFloat()
        mWidth = width.toFloat()
        mCenterX = mWidth/2
        mCenterY = mHeight/2
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //functions Call.......
    }
}