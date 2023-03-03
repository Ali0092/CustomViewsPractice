package com.example.android_practice_b

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*


private enum class Speed(val num: Int) {
    ONE(R.string.one),
    TWO(R.string.two),
    THREE(R.string.three),
    FOUR(R.string.four),
    FIVE(R.string.five),
    SIX(R.string.six),
    SEVEN(R.string.seven),
    EIGHT(R.string.eight),
    NINE(R.string.nine),
    ZERO(R.string.zero);

    fun next() = when (this) {
        ONE -> TWO
        TWO -> THREE
        THREE -> FOUR
        FOUR -> FIVE
        FIVE -> SIX
        SIX -> SEVEN
        SEVEN -> EIGHT
        EIGHT -> NINE
        NINE -> ZERO
        ZERO -> ONE
    }
}

class DialClassView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private var mHeight = 0
    private var mWidth = 0
    private var mRadius = 0
    private var mAngle = 0.0
    private var mCentreX = 0
    private var mCentreY = 0
    private var mPadding = 0
    private var mIsInit = false
    private lateinit var mPaint: Paint
    private var mPath: Path? = null
    private var mNumbers: IntArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var mMinimum = 0
    private var mHour = 0f
    private var mMinute = 0f
    private var mSecond = 0f
    private var mHourHandSize = 0
    private var mHandSize = 0
    private var mRect = Rect()
    private var imageBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.dial2)


    fun setBitmap(bitmap: Bitmap) {
        imageBitmap = Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, false)

        invalidate()
    }

    fun setNeedles(sN:Bitmap,mN:Bitmap,hN:Bitmap){
         //TODO.....
    }

    private fun init() {
        mHeight = height
        mWidth = width
        mPadding = 70
        mCentreX = mWidth / 2
        mCentreY = mHeight / 2
        mMinimum = Math.min(mHeight, mWidth)
        mRadius = mMinimum / 2 - mPadding
        mPaint = Paint()
        mPath = Path()
        mRect = Rect()
        mHourHandSize = mRadius - mRadius / 2
        mHandSize = mRadius - mRadius / 4
        mIsInit = true
    }


    @SuppressLint("DrawAllocation", "ResourceAsColor")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!mIsInit) {
            init()
        }

        val circledBitmap = drawDialBackground(canvas, imageBitmap)
        canvas.drawBitmap(circledBitmap, 0f, 0f, null)


        val mNeedleBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.needle_)

          val centerX = (mWidth - mNeedleBitmap.width) / 2
          val centerY = (mHeight - mNeedleBitmap.height) / 2

          val hourAngle = ((mHour + mMinute / 60.0) * 5f).toFloat()
          canvas.save();
          canvas.rotate(hourAngle, (mWidth/2).toFloat(), (mHeight / 2).toFloat());
          canvas.drawBitmap(mNeedleBitmap, centerX.toFloat(),
              centerY.toFloat()-60, mPaint);
          canvas.restore();

        drawHands(canvas)
        drawClockDialCenter(canvas)


        drawNumerals(canvas)

        val rec=Rect()
        Log.d("CHECK_COORDINATES","${mCentreX} ${mCentreY}")

        postInvalidateDelayed(500)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w
        mHeight = h
        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, mWidth, mHeight, false)
        Log.d("CHECK_SIZE", "$mWidth $mHeight")
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun drawDialBackground(canvas1: Canvas, bitmap: Bitmap): Bitmap {

        val output = Bitmap.createBitmap(
            mWidth, mHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val paint = Paint()
        paint.isAntiAlias = true

        val centerX = (mWidth - bitmap.width) / 2
        val centerY = (mHeight - bitmap.height) / 2

        canvas.drawCircle(mCentreX.toFloat(), mCentreY.toFloat(), mRadius.toFloat() + 50, mPaint!!)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, centerX.toFloat(), centerY.toFloat(), paint)
        canvas1.drawBitmap(output, 0f, 0f, null)

        return output
    }

    private fun drawClockDialCenter(canvas: Canvas) {
        // mPaint!!.reset()
        //setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 5)
        mPaint.color = Color.RED
        canvas.drawCircle(mCentreX.toFloat(), mCentreY.toFloat(), 15f, mPaint)
    }

    private fun drawHands(canvas: Canvas) {
        val calendar: Calendar = Calendar.getInstance()
        mHour = calendar.get(Calendar.HOUR_OF_DAY).toFloat()
        //convert to 12hour format from 24 hour format
        mHour = if (mHour > 12) mHour - 12 else mHour
        mMinute = calendar.get(Calendar.MINUTE).toFloat()
        mSecond = calendar.get(Calendar.SECOND).toFloat()

        // (mHour + (mMinute / 60.0f)) * 30.0f
        val hoursAngle = (mHour + (mMinute / 60.0)) * 30f
        val minutesAngle = mMinute * 6.0f
        val secondsAngle = mSecond * 6.0f

        drawHourHand(canvas, hoursAngle.toFloat())
        drawMinuteHand(canvas, minutesAngle)
        drawSecondsHand(canvas, secondsAngle)
    }

    private fun drawSecondsHand(canvas: Canvas, second: Float) {


        val mNeedleBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.s_needle)

        val centerX = (mWidth - mNeedleBitmap.width) / 2
        val centerY = (mHeight - mNeedleBitmap.height) / 2

        canvas.save();
        canvas.rotate(second, (mWidth / 2).toFloat(), (mHeight / 2).toFloat());
        canvas.drawBitmap(
            mNeedleBitmap, centerX.toFloat(),
            centerY.toFloat()-40, mPaint
        )
        canvas.restore();

        /* val paint = Paint()
         paint.color = Color.RED
         paint.style = Paint.Style.STROKE
         paint.strokeWidth = 2f


         mAngle = Math.PI * location / 30 - Math.PI / 2

         // here I have to set the bounds for the drawables.....
         canvas.drawLine(
             mCentreX.toFloat(),
             mCentreY.toFloat(),
             (mCentreX + Math.cos(mAngle) * mHandSize).toFloat() + 10,
             (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat() + 10,
             paint
         )*/
    }

    private fun drawMinuteHand(canvas: Canvas, minutes: Float) {
//        mPaint?.reset()
//        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 8)


        val mNeedleBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.m_needle)

        val centerX = (mWidth - mNeedleBitmap.width) / 2
        val centerY = (mHeight - mNeedleBitmap.height) / 2

        canvas.save();
        canvas.rotate(minutes, (mWidth / 2).toFloat(), (mHeight / 2).toFloat());
        canvas.drawBitmap(
            mNeedleBitmap, centerX.toFloat(),
            centerY.toFloat() - 40, mPaint
        );
        canvas.restore();


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

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun drawHourHand(canvas: Canvas, location: Float) {
        /*mPaint!!.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 10)*/


        val mNeedleBitmap = BitmapFactory.decodeResource(context?.resources, R.drawable.h_needle)

        val centerX = (mWidth - mNeedleBitmap.width) / 2
        val centerY = (mHeight - mNeedleBitmap.height) / 2

        canvas.save();
        canvas.rotate(location, (mWidth / 2).toFloat(), (mHeight / 2).toFloat());
        canvas.drawBitmap(
            mNeedleBitmap, centerX.toFloat(),
            centerY.toFloat() - 37, mPaint
        );
        canvas.restore();

        /* val paint = Paint()
         paint.color = Color.BLACK
         paint.style = Paint.Style.STROKE
         paint.strokeWidth = 10f
         mAngle = Math.PI * location / 30 - Math.PI / 2

         canvas.drawLine(
             mCentreX.toFloat(),
             mCentreY.toFloat(),
             (mCentreX + Math.cos(mAngle) * mHourHandSize).toFloat(),
             (mCentreY + Math.sin(mAngle) * mHourHandSize).toFloat(),
             paint
         )*/
    }

    private fun drawNumerals(canvas: Canvas) {

        val mRect = Rect()
        mPaint.textSize = 30f
        for (number in mNumbers) {
            val num = number.toString()
            mPaint.strokeWidth = 3f
            mPaint.getTextBounds(num, 0, num.length, mRect)
            val angle = Math.PI / 6 * (number - 3)
            val x = (mCentreX + Math.cos(angle) * mRadius - mRect.width() / 2).toInt()
            val y = (mCentreY + Math.sin(angle) * mRadius + mRect.height() / 2).toInt()
            canvas.drawText(num, x.toFloat(), y.toFloat(), mPaint)

        }
    }


}


