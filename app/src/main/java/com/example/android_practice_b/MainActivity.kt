package com.example.android_practice_b

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.android_practice_b.databinding.ActivityMainBinding
import com.example.android_practice_b.wallpaper.ClockWallpaperService
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dstroke = 5f
        var dcolor = 0
        var dneon = true
        var ncolor = 0
        var nsize = 30f
        var nstroke = 1f
        var nneon = true
        var handsNeon = true
        var hColor = 0
        var mColor = 0
        var bgradius = 50f
        var bgStyle = 0
        var bgcolor = 0



        binding.myClock.setOnClickListener {
            val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
            )
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, ClockWallpaperService::class.java)
            )
            startActivity(intent)
            finish()
        }


        binding.bgColor.setOnClickListener {
            if (bgcolor > 5) bgcolor = 0
            val bgc = when (bgcolor) {
                0 -> Color.WHITE
                1 -> Color.YELLOW
                2 -> Color.CYAN
                3 -> Color.BLACK
                4 -> Color.LTGRAY
                else -> Color.RED
            }
            binding.myClock.changeBgColor(bgc)
            MyUtils.changeBgColor(bgc)
            bgcolor++
        }

        binding.bgRadius.setOnClickListener {
            if (bgradius > 150) bgradius = 50f

            binding.myClock.changeBgRadius(bgradius)
            MyUtils.bgRadius  = bgradius
            bgradius += 10f
        }

        binding.bgStyle.setOnClickListener {
            if (bgStyle > 3) bgStyle = 0

            val style = when (bgStyle) {
                0 -> BlurMaskFilter.Blur.OUTER
                1 -> BlurMaskFilter.Blur.INNER
                2 -> BlurMaskFilter.Blur.SOLID
                else -> BlurMaskFilter.Blur.NORMAL
            }

            binding.myClock.changeBgStyle(style)
            MyUtils.bgEffectPaint.maskFilter = BlurMaskFilter(bgradius, style)
            bgStyle++

        }




        binding.needleNeon.setOnClickListener {
            binding.myClock.enableNeonNeedles(handsNeon)


            if (handsNeon) {
                MyUtils.needlesPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)
            } else {
                MyUtils.needlesPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
            }
            handsNeon = !handsNeon
        }

        binding.hourColor.setOnClickListener {
            if (hColor > 5) hColor = 0
            val hc = when (hColor) {
                0 -> Color.WHITE
                1 -> Color.YELLOW
                2 -> Color.CYAN
                3 -> Color.BLACK
                4 -> Color.LTGRAY
                else -> Color.RED
            }
            binding.myClock.changeHourNeedleColor(hc)
            MyUtils.hNeedleColor = hc

            hColor++
        }


        binding.minColor.setOnClickListener {
            if (mColor > 5) mColor = 0
            val mc = when (mColor) {
                0 -> Color.WHITE
                1 -> Color.YELLOW
                2 -> Color.CYAN
                3 -> Color.BLACK
                4 -> Color.LTGRAY
                else -> Color.RED
            }
            binding.myClock.changeMinutesNeedlesColor(mc)

            MyUtils.mNeedleColor = mc
            mColor++
        }



        binding.dialStroke.setOnClickListener {
            if (dstroke > 20) dstroke = 1f
            binding.myClock.changeDialStroke(dstroke)
            MyUtils.dialPaint.strokeWidth = dstroke
            dstroke += 2
        }


        binding.dialColor.setOnClickListener {

            if (dcolor > 5) dcolor = 0
            val dc = when (dcolor) {
                0 -> Color.WHITE
                1 -> Color.YELLOW
                2 -> Color.CYAN
                3 -> Color.BLACK
                4 -> Color.LTGRAY
                else -> Color.RED
            }
            binding.myClock.changeDialColor(dc)
            MyUtils.dialPaint.color = dc
            dcolor++
        }

        binding.dialNeon.setOnClickListener {
            binding.myClock.enableNeonDial(dneon)
            if (dneon) {
                MyUtils.dialPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)
            } else {
                MyUtils.dialPaint.maskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
            }
            dneon = !dneon
        }

        binding.numeralColor.setOnClickListener {

            if (ncolor > 5) ncolor = 0
            val nc = when (ncolor) {
                0 -> Color.WHITE
                1 -> Color.YELLOW
                2 -> Color.CYAN
                3 -> Color.BLACK
                4 -> Color.LTGRAY
                else -> Color.RED
            }
            binding.myClock.changeNumeralColor(nc)
            MyUtils.numeralPaint.color = nc
            ncolor++
        }

        binding.numeralSize.setOnClickListener {
            if (nsize > 80) nsize = 25f
            binding.myClock.changeNumeralSize(nsize)
            MyUtils.numeralPaint.textSize = nsize
            nsize += 5f
        }

        binding.numeralStroke.setOnClickListener {
            if (nstroke > 5) nstroke = 1f
            binding.myClock.changeNumeralStroke(nstroke)
            MyUtils.numeralPaint.strokeWidth = nstroke
            nstroke++
        }

        binding.numeralNeon.setOnClickListener {
            binding.myClock.enableNeonNumerals(nneon)
            if (nneon) {
                MyUtils.numeralPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)
            } else {
                MyUtils.numeralPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)
            }
            nneon = !nneon

        }



        /*   binding.textSize.setOnClickListener {


               val intent = Intent(
                   WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
               )
               intent.putExtra(
                   WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                   ComponentName(this, ClockWallpaperService::class.java)
               )
               startActivity(intent)
               finish()
           }*/

        /*  binding.colorsPicker.setOnClickListener {
              CustomGradientSelectorDialog(
                  this,
                  Color.RED,
                  object :
                      CustomGradientSelectorDialog.OnAmbilWarnaListener {
                      override fun onCancel(dialog: CustomGradientSelectorDialog?) {
                          dialog?.dialog?.dismiss()
                      }

                      override fun onOk(
                          dialog: CustomGradientSelectorDialog?,
                          color1: Int,
                          color2: Int
                      ) {


                           binding.customText.setGradientColors(color2,color1)
                         *//* binding.gradientView.start = color1
                       binding.gradientView.end = color2*//*
                       Toast.makeText(this@MainActivity, "$", Toast.LENGTH_SHORT)
                           .show()
                       dialog?.dialog?.dismiss()
                   }

               }).show()
       }


        var textSize = 35f
        var stroke = 1f
        var lineSp = 1.5f
        var letterSp = 0.05f
        var align = 0
        var just = 0
        var combo = 1
        var angle = 90f
*/

        /* binding.textSize.setOnClickListener {
             if (textSize > 55) textSize = 30f
             binding.customText.setTextSixe(textSize)
             textSize++
         }*/

        /*      binding.strokeSize.setOnClickListener {
                  if (stroke > 6) stroke = 3f
                  binding.customText.setStroke(stroke)
                  stroke+=1f
              }
      */

    }


}