package com.example.android_practice_b.wallpaper

import android.graphics.*
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.example.android_practice_b.MyUtils
import com.example.android_practice_b.MyUtils.Companion.hNeedleColor
import com.example.android_practice_b.MyUtils.Companion.mNeedleColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ClockWallpaperService : WallpaperService() {

    private val handler = Handler()

    val dialPaint = MyUtils.dialPaint
    val numeralPaint = MyUtils.numeralPaint
    val needlesPaint = MyUtils.needlesPaint
    val bgEffectPaint = MyUtils.bgEffectPaint
    var bgRadius = MyUtils.bgRadius
    var mHandColor = MyUtils.mNeedleColor
    var hHandColor = MyUtils.hNeedleColor
    var cPaint = Paint()


    override fun onCreateEngine(): Engine {
        return ClockWallpaperEngine()
    }

    private inner class ClockWallpaperEngine : Engine() {

        val drawRunnable = Runnable {
            kotlin.run {
                drawGradient()
            }
        }

        var radius = 250f

        var isVisibleView = false
        private var centerX = 0f
        private var centerY = 0f
        lateinit var myHolder: SurfaceHolder
        private var mNumbers: IntArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        var mHourHandSize = radius - radius / 2
        var mHandSize = radius - radius / 4

        override fun onDestroy() {
            super.onDestroy()
            handler.removeCallbacks(drawRunnable)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            isVisibleView = visible
            if (isVisibleView) {
                drawGradient()
            } else {
                handler.removeCallbacks(drawRunnable)
            }
        }


        override fun onSurfaceChanged(
            holder: SurfaceHolder,
            format: Int,
            width: Int,
            height: Int
        ) {
            super.onSurfaceChanged(holder, format, width, height)
            myHolder = holder

            centerX = width.toFloat() / 2
            centerY = height.toFloat() / 2

            Log.d("RUNN_CHECK","Running......")
            drawGradient()

        }


        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            isVisibleView = false
            handler.removeCallbacks(drawRunnable)
        }

        private fun drawGradient() {

            val canvas = myHolder.lockCanvas()
            canvas.drawColor(0, PorterDuff.Mode.CLEAR)

            canvas.drawCircle(centerX, centerY, radius, dialPaint)
            canvas.drawCircle(centerX, centerY, bgRadius, bgEffectPaint)
            drawNumerals(canvas, centerX, centerY, radius - 40)
            drawHands(canvas)

            cPaint.style = Paint.Style.FILL
            cPaint.color = dialPaint.color
            //dialPaint.style = Paint.Style.FILL
            canvas.drawCircle(centerX, centerY, 10f, cPaint)

            myHolder.unlockCanvasAndPost(canvas)
            if (isVisibleView) {
                handler.postDelayed(drawRunnable, 200)
            }
        }

        private fun drawNumerals(canvas: Canvas, cX: Float, cY: Float, r: Float) {

            val rect = Rect()

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
                centerX,
                centerY,
                (centerX + Math.cos(mAngle) * mHandSize).toFloat(),
                (centerY + Math.sin(mAngle) * mHourHandSize).toFloat(),
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
            needlesPaint.color = mHandColor

            //paint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)

            val mAngle = Math.PI * minutes / 30 - Math.PI / 2

            // here I have to set the bounds for the drawables.....
            canvas.drawLine(
                centerX,
                centerY,
                (centerX + Math.cos(mAngle) * mHandSize).toFloat(),
                (centerY + Math.sin(mAngle) * mHourHandSize).toFloat(),
                needlesPaint
            )

        }

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
            needlesPaint.color = hHandColor

            //paint.maskFilter = BlurMaskFilter(40f, BlurMaskFilter.Blur.SOLID)

            val mAngle = Math.PI * location / 30 - Math.PI / 2

            canvas.drawLine(
                centerX,
                centerY,
                (centerX + Math.cos(mAngle) * mHourHandSize).toFloat(),
                (centerY + Math.sin(mAngle) * mHourHandSize).toFloat(),
                needlesPaint
            )


        }


    }

}