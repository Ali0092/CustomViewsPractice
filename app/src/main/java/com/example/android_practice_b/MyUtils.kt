package com.example.android_practice_b

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Paint

class MyUtils {

    companion object {

        val dialPaint = Paint()
        val numeralPaint = Paint()
        val needlesPaint = Paint()
        val bgEffectPaint = Paint()

        var hNeedleColor = Color.CYAN
        var mNeedleColor = Color.GREEN
        var bgRadius = 50f
        var bgColor = Color.YELLOW

        init {
            dialPaint.apply {
                style = Paint.Style.STROKE
                isAntiAlias = true
                color = Color.RED
                strokeWidth = 5f
            }

            //NUMERALS PAINT INITIALIZATION.....
            numeralPaint.style = Paint.Style.STROKE
            numeralPaint.color = Color.RED
            numeralPaint.textSize = 25f
            numeralPaint.isAntiAlias = true

            //NEEDLES PAINT INITIALIZATION....
            needlesPaint.style = Paint.Style.STROKE
            needlesPaint.strokeWidth = 10f
            needlesPaint.isAntiAlias = true
            needlesPaint.color = Color.BLACK
            needlesPaint.strokeCap = Paint.Cap.ROUND

            //BG EFFECT PAINT INITIALIZATION....
            bgEffectPaint.color = bgColor
            bgEffectPaint.isAntiAlias = true
            bgEffectPaint.style = Paint.Style.FILL
            bgEffectPaint.maskFilter = BlurMaskFilter(bgRadius, BlurMaskFilter.Blur.SOLID)

        }


        //DIAL CIRCLE CUSTOMIZATION FUNCTIONS........
        fun changeDialColor(color: Int) {
            dialPaint.color = color
        }

        fun changeDialStroke(stroke: Float) {
            dialPaint.strokeWidth = stroke
        }

        fun changeDialStyle(style:  BlurMaskFilter.Blur) {
            dialPaint.maskFilter = BlurMaskFilter(30f,style)
        }

        fun enableNeonDial(setNeon: Boolean) {
            if (setNeon) {
                dialPaint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID)
            } else {
                dialPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
            }

        }

        //NUMERALS CUSTOMIZATION FUNCTIONS.........
        fun changeNumeralColor(color: Int) {
            numeralPaint.color = when (color) {
                0 -> Color.RED
                1 -> 0xff00e5ff.toInt()
                else -> Color.YELLOW
            }

        }

        fun changeNumeralStroke(stroke: Float) {
            numeralPaint.strokeWidth = stroke
        }

        fun changeNumeralSize(size: Float) {
            numeralPaint.textSize = size
        }

        fun enableNeonNumerals(setNeon: Boolean) {
            if (setNeon) {
                numeralPaint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID)
            } else {
                numeralPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
            }

        }

        //NEEDLES CUSTOMIZATION FUNCTIONS........
        fun changeNeedlesColor(hColor: Int, mColor: Int) {

            hNeedleColor = when (hColor) {
                0 -> Color.BLACK
                1 -> 0xff00e5ff.toInt()
                else -> Color.YELLOW
            }

            mNeedleColor = when (mColor) {
                0 -> Color.BLACK
                1 -> 0xff00e5ff.toInt()
                else -> Color.YELLOW
            }

        }

        fun enableNeonNeedles(setNeon: Boolean) {
            if (setNeon) {
                needlesPaint.maskFilter = BlurMaskFilter(25f, BlurMaskFilter.Blur.SOLID)
            } else {
                needlesPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
            }
        }

        //BG EFFECT CUSTOMIZATION..........
        fun changeBgColor(color: Int) {
            bgEffectPaint.color = color
           /* bgEffectPaint.color = when (color) {
                0 -> Color.BLACK
                1 -> 0xff00e5ff.toInt()
                else -> Color.YELLOW
            }*/
        }

        fun enableNeonBG(state: Boolean) {
            bgEffectPaint.reset()

            if (state) {
                bgEffectPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.NORMAL)
            } else {
                bgEffectPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.NORMAL)
            }

        }

        fun changeBgStyle(sNo: Int) {
            when (sNo) {
                0 -> bgEffectPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.NORMAL)
                1 -> bgEffectPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.INNER)
                2 -> bgEffectPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.OUTER)
                else -> bgEffectPaint.maskFilter =
                    BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)
            }
        }

        fun resetDialChanges() {
            dialPaint.style = Paint.Style.STROKE
            dialPaint.isAntiAlias = true
            dialPaint.color = Color.BLACK
        }

        fun resetNumeralChanges() {
            numeralPaint.style = Paint.Style.FILL_AND_STROKE
            numeralPaint.color = Color.WHITE
            numeralPaint.textSize = 25f
            numeralPaint.isAntiAlias = true
        }

        fun resetNeedlesChanges() {
            needlesPaint.style = Paint.Style.STROKE
            needlesPaint.strokeWidth = 10f
            needlesPaint.isAntiAlias = true
            needlesPaint.color = Color.WHITE
        }

        fun resetBGChanges() {
            bgEffectPaint.color = Color.WHITE
            bgEffectPaint.isAntiAlias = true
            bgEffectPaint.style = Paint.Style.FILL
            bgEffectPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.NORMAL)
        }


    }

}