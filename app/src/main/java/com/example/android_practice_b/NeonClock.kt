package com.example.android_practice_b

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import java.lang.Integer.min
import java.util.*

class NeonClock(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet){

    lateinit var rect: Rect

    val dialPaint = Paint()
    val numeralPaint = Paint()
    val needlesPaint = Paint()
    val bgEffectPaint = Paint()

    var mCentreX = 0f
    var mCentreY = 0f
    var radius = 0f
    var mWidth = 0
    var mHeight = 0
    var hasInitialized = false
    var mHourHandSize = 0f
    var mHandSize = 0f

    //variables for customization.....
    var neonDial = false
    var neonNeedles = false
    var neonBg = false
    var mColor = Color.BLACK
    var colorI = 1
    var hNeedleColor = Color.BLACK
    var mNeedleColor = Color.BLACK
    var sNeedleColor = Color.RED

    var bgRadius = 50f
    var bgNeonRadius = 100f

    private var mNumbers: IntArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)


    fun init() {

        mHourHandSize = radius - radius / 2
        mHandSize = radius - radius / 4


        mWidth = width
        mHeight = height

        mCentreX = (width / 2).toFloat()
        mCentreY = (height / 2).toFloat()
        radius = (min(width, height) / 2).toFloat() - 50


        //Initializing Rect() with (0,0,0,0)....
        rect = Rect()

        //DIAL PAINT INITIALIZER............
        dialPaint.style = Paint.Style.STROKE
        dialPaint.isAntiAlias = true
        dialPaint.color = Color.RED

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
        bgEffectPaint.color = Color.BLACK
        bgEffectPaint.isAntiAlias = true
        bgEffectPaint.style = Paint.Style.FILL
        bgEffectPaint.maskFilter = BlurMaskFilter(bgNeonRadius, BlurMaskFilter.Blur.NORMAL)

        hasInitialized = true

    }

    /*init {
        holder.addCallback(this)
    }*/

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (min(w, h) / 2).toFloat() - 50
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //Initializing Views...
        if (!hasInitialized) {
            init()
        }

        dialPaint.style = Paint.Style.STROKE
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, dialPaint)
        canvas.drawCircle(mCentreX.toFloat(), mCentreY.toFloat(), bgRadius, bgEffectPaint)
        drawNumerals(canvas, mCentreX, mCentreY, radius - 40)
        drawHands(canvas)
        dialPaint.style = Paint.Style.FILL
        canvas.drawCircle(mCentreX, mCentreY, 10f, dialPaint)
        postInvalidateDelayed(500)
    }


    private fun drawNumerals(canvas: Canvas, cX: Float, cY: Float, r: Float) {


        for (number in mNumbers) {
            val num = number.toString()
            numeralPaint.getTextBounds(num, 0, num.length, rect)
            val angle = Math.PI / 6 * (number - 3)
            val x = (cX + Math.cos(angle) * r - rect.width() / 2).toInt()
            val y = (cY + Math.sin(angle) * r + rect.height() / 2).toInt()
            canvas.drawText(num, x.toFloat(), y.toFloat(), numeralPaint)

        }
    }

    private fun drawHands(canvas: Canvas) {
        val calendar: Calendar = Calendar.getInstance()
        var mHour = calendar.get(Calendar.HOUR_OF_DAY).toFloat()
        //convert to 12hour format from 24 hour format
        mHour = if (mHour > 12) mHour - 12 else mHour
        val mMinute = calendar.get(Calendar.MINUTE).toFloat()
        val mSecond = calendar.get(Calendar.SECOND).toFloat()

        // (mHour + (mMinute / 60.0f)) * 30.0f
        val hoursAngle = (mHour + (mMinute / 60.0)) * 5f
        val minutesAngle = mMinute
        val secondsAngle = mSecond

        drawHourHand(canvas, hoursAngle.toFloat())
        drawMinuteHand(canvas, minutesAngle)
        drawSecondsHand(canvas, secondsAngle)
    }

    private fun drawSecondsHand(canvas: Canvas, second: Float) {

        /* val mNeedleBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.s_needle)

         val centerX = (mWidth - mNeedleBitmap.width) / 2
         val centerY = (mHeight - mNeedleBitmap.height) / 2
         canvas.save();
         canvas.rotate(second, (mWidth / 2).toFloat(), (mHeight / 2).toFloat());
         canvas.drawBitmap(
             mNeedleBitmap, centerX.toFloat(),
             centerY.toFloat()-40, paint
         )
         canvas.restore()*/

        // paint.reset()
        needlesPaint.strokeWidth = 4f
        needlesPaint.color = Color.RED


        /* val paint = Paint()
         paint.color = mColor.toInt()
         paint.style = Paint.Style.STROKE
         paint.strokeWidth = 5f

         paint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)
 */
        val mAngle = Math.PI * second / 30 - Math.PI / 2

        // here I have to set the bounds for the drawables.....
        canvas.drawLine(
            mCentreX.toFloat(),
            mCentreY.toFloat(),
            (mCentreX + Math.cos(mAngle) * mHandSize).toFloat(),
            (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
            needlesPaint
        )
    }

    private fun drawMinuteHand(canvas: Canvas, minutes: Float) {
//        mPaint?.reset()
//        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 8)


        /* val mNeedleBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.m_needle)

         val centerX = (mWidth - mNeedleBitmap.width) / 2
         val centerY = (mHeight - mNeedleBitmap.height) / 2

         canvas.save();
         canvas.rotate(minutes, (mWidth / 2).toFloat(), (mHeight / 2).toFloat());
         canvas.drawBitmap(
             mNeedleBitmap, centerX.toFloat(),
             centerY.toFloat() - 40, paint
         );
         canvas.restore();*/


        /*  val paint = Paint()
          paint.color = Color.BLACK
          paint.style = Paint.Style.STROKE
          paint.strokeWidth = 7f
          mAngle = Math.PI * location / 30 - Math.PI / 2;

          canvas.drawLine(
              mCentreX.toFloat(),
              mCentreY.toFloat(),
              (mCentreX + Math.cos(mAngle) * mHandSize).toFloat(),
              (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
              paint
          )*/

        /* val paint = Paint()
         paint.color = mColor.toInt()
         paint.style = Paint.Style.STROKE*/

        //    paint.reset()
        needlesPaint.strokeWidth = 7.5f
        needlesPaint.color = mNeedleColor

        //paint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)

        val mAngle = Math.PI * minutes / 30 - Math.PI / 2

        // here I have to set the bounds for the drawables.....
        canvas.drawLine(
            mCentreX.toFloat(),
            mCentreY.toFloat(),
            (mCentreX + Math.cos(mAngle) * mHandSize).toFloat(),
            (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
            needlesPaint
        )

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun drawHourHand(canvas: Canvas, location: Float) {
        /*mPaint!!.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 10)*/


        /* val mNeedleBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.h_needle)

         val centerX = (mWidth - mNeedleBitmap.width) / 2
         val centerY = (mHeight - mNeedleBitmap.height) / 2

         canvas.save();
         canvas.rotate(location, (mWidth / 2).toFloat(), (mHeight / 2).toFloat());
         canvas.drawBitmap(
             mNeedleBitmap, centerX.toFloat(),
             centerY.toFloat() - 37, paint
         );
         canvas.restore();
 */
        /* val paint = Paint()
         paint.color = mColor.toInt()
         paint.style = Paint.Style.STROKE*/

        // paint.reset()
        needlesPaint.strokeWidth = 12f
        needlesPaint.color = hNeedleColor

        //paint.maskFilter = BlurMaskFilter(40f, BlurMaskFilter.Blur.SOLID)

        val mAngle = Math.PI * location / 30 - Math.PI / 2

        canvas.drawLine(
            mCentreX.toFloat(),
            mCentreY.toFloat(),
            (mCentreX + Math.cos(mAngle) * mHourHandSize).toFloat(),
            (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
            needlesPaint
        )


    }


    //DIAL CIRCLE CUSTOMIZATION FUNCTIONS........
    fun changeDialColor(color: Int) {


        dialPaint.color = color
      /*  dialPaint.color = when (color) {
            0 -> Color.BLACK
            1 -> 0xff00e5ff.toInt()
            else -> Color.YELLOW
        }*/

        invalidate()
    }

    fun changeDialStroke(stroke: Float) {
        dialPaint.strokeWidth = stroke
        invalidate()
    }

    fun changeDialStyle(sNo: Int) {
        when (sNo) {
            0 -> dialPaint.maskFilter = BlurMaskFilter(bgNeonRadius, BlurMaskFilter.Blur.NORMAL)
            1 -> dialPaint.maskFilter = BlurMaskFilter(bgNeonRadius, BlurMaskFilter.Blur.INNER)
            2 -> dialPaint.maskFilter = BlurMaskFilter(bgNeonRadius, BlurMaskFilter.Blur.OUTER)
            else -> dialPaint.maskFilter =
                BlurMaskFilter(bgNeonRadius, BlurMaskFilter.Blur.SOLID)
        }
        invalidate()
    }


    fun enableNeonDial(setNeon: Boolean) {
        if (setNeon) {
            dialPaint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID)
        } else {
            dialPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
        }
        invalidate()

    }

    //NUMERALS CUSTOMIZATION FUNCTIONS.........
    fun changeNumeralColor(color: Int) {

        numeralPaint.color = color
        /*numeralPaint.color = when (color) {
            0 -> Color.RED
            1 -> 0xff00e5ff.toInt()
            else -> Color.YELLOW
        }*/

        invalidate()
    }


    fun changeNumeralStroke(stroke: Float) {
        numeralPaint.strokeWidth = stroke
        invalidate()
    }

    fun changeNumeralSize(size: Float) {
        numeralPaint.textSize = size
        invalidate()
    }

    fun enableNeonNumerals(setNeon: Boolean) {
        if (setNeon) {
            numeralPaint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID)
        } else {
            numeralPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
        }
        invalidate()

    }

    //NEEDLES CUSTOMIZATION FUNCTIONS........

    fun changeHourNeedleColor(hColor: Int){
        hNeedleColor = hColor
        invalidate()
    }
    fun changeMinutesNeedlesColor( mColor: Int) {

        mNeedleColor = mColor

        invalidate()
    }

    fun enableNeonNeedles(setNeon: Boolean) {
        if (setNeon) {
            needlesPaint.maskFilter = BlurMaskFilter(25f, BlurMaskFilter.Blur.SOLID)
        } else {
            needlesPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
        }
        invalidate()
    }


    //BG EFFECT CUSTOMIZATION..........
    fun changeBgColor(color: Int) {
        bgEffectPaint.color = color
        invalidate()
    }

    fun changeBgRadius(radius: Float) {
        bgRadius = radius
    }

    fun enableNeonBG(state: Boolean) {
        bgEffectPaint.reset()

        if (state) {
            bgEffectPaint.maskFilter = BlurMaskFilter(bgNeonRadius, BlurMaskFilter.Blur.NORMAL)
        } else {
            bgEffectPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.NORMAL)
        }

        invalidate()
    }

    fun changeNeonDensity(density: Float) {
        bgNeonRadius = density
        invalidate()
    }

    fun changeBgStyle(style:  BlurMaskFilter.Blur) {

        bgEffectPaint.maskFilter = BlurMaskFilter(bgNeonRadius, style)
        invalidate()
    }

    fun resetDialChanges() {
        dialPaint.style = Paint.Style.STROKE
        dialPaint.isAntiAlias = true
        dialPaint.color = Color.BLACK
        invalidate()
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
        bgEffectPaint.maskFilter = BlurMaskFilter(bgNeonRadius, BlurMaskFilter.Blur.NORMAL)
    }

  /*  override fun surfaceCreated(holder: SurfaceHolder) {
       invalidate()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        invalidate()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}*/

}