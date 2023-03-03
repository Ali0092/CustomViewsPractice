package com.example.android_practice_b

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.text.LineBreaker
import android.os.Build
import android.text.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi


class GradientText(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {


    val str =
        "This is gradient Color text which will draw multiple lines on canvas and you can customize its attributes"
    val paint = TextPaint()


    var color1 = Color.YELLOW
    var color2 = Color.CYAN

    var align = Layout.Alignment.ALIGN_CENTER
    var stroke =1f
    var textSize = 30f
    var justification = LineBreaker.JUSTIFICATION_MODE_NONE
    var lineSpace = 1.0f
    var letterSpace = 0.05f
    var mRotate = 0f



    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.textSize = textSize
        paint.color = Color.BLACK;
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = stroke
        paint.letterSpacing = letterSpace


        setBackgroundColor(Color.BLACK)




        val gradient: LinearGradient = LinearGradient(
            0f, 0f, 50f, 50f ,
            color1, color2, Shader.TileMode.REPEAT
        )


        paint.shader = gradient


        val myStaticLayout = StaticLayout.Builder.obtain(str, 0, str.length, paint, width)
            .setAlignment(align)
            .setJustificationMode(justification)
            .setLineSpacing(0f, lineSpace)
            .setBreakStrategy(LineBreaker.BREAK_STRATEGY_BALANCED)
            .build()



        canvas.save()
        canvas.rotate(mRotate,width / 2f,height  / 2f)

        canvas.translate(
            (width - myStaticLayout.width) / 2f,
            (height - myStaticLayout.height) / 2f
        )
        myStaticLayout.draw(canvas)


        canvas.restore()


    }


    fun setCanvasRotation(r:Float){
        mRotate = r
        invalidate()
    }

    fun setTextSixe(size: Float) {
        textSize = size
        invalidate()
    }

    @JvmName("setStroke1")
    fun setStroke(s: Float) {
        stroke = s
        invalidate()
    }


    fun setAlign(n: Int) {
        align = when (n) {
            0 -> Layout.Alignment.ALIGN_NORMAL
            1 -> Layout.Alignment.ALIGN_CENTER
            else -> Layout.Alignment.ALIGN_OPPOSITE
        }
        invalidate()
    }


    @JvmName("setJustification1")
    @RequiresApi(Build.VERSION_CODES.Q)
    fun setJustification(n: Int) {
        justification = if (n == 0) {
            LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        } else LineBreaker.JUSTIFICATION_MODE_NONE
        invalidate()
    }

    fun setLineSpacing(x: Float) {
        lineSpace = x
        invalidate()
    }

    fun setLetterSpacing(x: Float){
        letterSpace = x
        invalidate()
    }

    fun setGradientColors(c1: Int, c2: Int) {
        color1 = c1
        color2 = c2
        invalidate()
    }


/*    fun setStyle(x: Int) {
        gradientMode = when (x) {
            0 -> Shader.TileMode.CLAMP
            1 -> Shader.TileMode.REPEAT
            else -> Shader.TileMode.MIRROR
        }
        invalidate()
    }*/


    fun setSolidColors(c:Int){
        color1 = c
        color2 = c
        invalidate()
    }
}



















