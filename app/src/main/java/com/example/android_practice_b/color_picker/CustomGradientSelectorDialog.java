package com.example.android_practice_b.color_picker;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;

import com.example.android_practice_b.R;
import com.example.android_practice_b.Utils;
import com.mikhaellopez.gradientview.GradientView;


public class CustomGradientSelectorDialog {

    public interface OnAmbilWarnaListener {
        void onCancel(CustomGradientSelectorDialog dialog);

        void onOk(CustomGradientSelectorDialog dialog, int g1,int g2);
    }

    final AlertDialog dialog;
    final OnAmbilWarnaListener listener;

    final View colorPallet;
    final ColorShaderBox colorShaderBox;
    final GradientView selectedGradientColor;
    final ImageView shader_hand1;
    final ImageView shader_hand2;


    Utils utils = Utils.INSTANCE;

    Boolean move1 = true;
    Boolean move2 = false;
    public Integer color1 = Color.BLACK;
    public Integer color2 = Color.WHITE;

    final ImageView pallet_hand;

    final ViewGroup viewsContainer;
    final float[] currentColorHsv = new float[3];
    int alpha;


    public CustomGradientSelectorDialog(final Context context, int color, OnAmbilWarnaListener listener) {
        this(context, color, false, listener);
    }

    @SuppressLint("ClickableViewAccessibility")
    public CustomGradientSelectorDialog(final Context context, int color, boolean supportsAlpha, OnAmbilWarnaListener listener) {
        this.listener = listener;

        if (!supportsAlpha) { // remove alpha if not supported
            color = color | 0xff000000;
        }

        Color.colorToHSV(color, currentColorHsv);
        alpha = Color.alpha(color);
        utils.setGradientColor1(color2);
        utils.setGradientColor2(color1);

        final View view = LayoutInflater.from(context).inflate(R.layout.custom_color_picker_layout, null);

        colorPallet = view.findViewById(R.id.color_pallete_bar);
        colorShaderBox = (ColorShaderBox) view.findViewById(R.id.color_shader_box);
        selectedGradientColor = view.findViewById(R.id.gradientView);

        viewsContainer = (ViewGroup) view.findViewById(R.id.views_container_parent_layout);


        pallet_hand = (ImageView) view.findViewById(R.id.ambilwarna_cursor);
        shader_hand1 = (ImageView) view.findViewById(R.id.ambilwarna_target1);
        shader_hand2 = (ImageView) view.findViewById(R.id.ambilwarna_target2);

        colorShaderBox.setHue(getHue());
        selectedGradientColor.setStart(color2);
        selectedGradientColor.setEnd(color1);


        colorPallet.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE
                        || event.getAction() == MotionEvent.ACTION_DOWN
                        || event.getAction() == MotionEvent.ACTION_UP) {

                    float y = event.getY();
                    if (y < 0.f) y = 0.f;
                    if (y > colorPallet.getMeasuredHeight()) {
                        y = colorPallet.getMeasuredHeight() - 0.001f; // to avoid jumping the cursor from bottom to top.
                    }
                    float hue = 360.f - 360.f / colorPallet.getMeasuredHeight() * y;
                    if (hue == 360.f) hue = 0.f;
                    setHue(hue);

                    // update view
                    colorShaderBox.setHue(getHue());
                    moveCursor();


                    color1 = getColor();
                    color2 = getColor();


                    // selectedColor1.setCardBackgroundColor(getColor());
                   // selectedColor2.setCardBackgroundColor(getColor());

                    //setting hex color here
                    // hexCode.setText(getHexColor(getColor()));

                    return true;
                }
                return false;
            }
        });


        colorShaderBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (move1) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE
                            || event.getAction() == MotionEvent.ACTION_DOWN) {

                        float x = event.getX(); // touch event are in dp units.
                        float y = event.getY();

                        if (x < 0.f) x = 0.f;
                        if (x > colorShaderBox.getMeasuredWidth())
                            x = colorShaderBox.getMeasuredWidth();
                        if (y < 0.f) y = 0.f;
                        if (y > colorShaderBox.getMeasuredHeight())
                            y = colorShaderBox.getMeasuredHeight();

                        setSat(1.f / colorShaderBox.getMeasuredWidth() * x);
                        setVal(1.f - (1.f / colorShaderBox.getMeasuredHeight() * y));

                        // update view
                        moveTarget();

                        color1 = getColor();
                        //utils.setGradientColor1(color1);

                        selectedGradientColor.setEnd(color1);

                        //selectedColor1.setCardBackgroundColor(color1);
                        /// hexCode.setText(getHexColor(getColor()));...................................

                    }

                    if( event.getAction() == MotionEvent.ACTION_UP){
                        move2 = true;
                        move1 = false;
                        Log.d("SHADER_DETECTOR","1");

                        return true;
                    }
                }

                if (move2) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE
                            || event.getAction() == MotionEvent.ACTION_DOWN
                            || event.getAction() == MotionEvent.ACTION_UP) {

                        float x = event.getX(); // touch event are in dp units.
                        float y = event.getY();

                        if (x < 0.f) x = 0.f;
                        if (x > colorShaderBox.getMeasuredWidth())
                            x = colorShaderBox.getMeasuredWidth();
                        if (y < 0.f) y = 0.f;
                        if (y > colorShaderBox.getMeasuredHeight())
                            y = colorShaderBox.getMeasuredHeight();

                        setSat(1.f / colorShaderBox.getMeasuredWidth() * x);
                        setVal(1.f - (1.f / colorShaderBox.getMeasuredHeight() * y));

                        // update view

                        moveTarget2();

                        color2 = getColor();
                        selectedGradientColor.setStart(color2);
                       // utils.setGradientColor2(color2);

                        //  selectedColor2.setCardBackgroundColor(color2);
                        /// hexCode.setText(getHexColor(getColor()));...................................

                    }
                    if( event.getAction() == MotionEvent.ACTION_UP){
                        move1 = true;
                        move2 = false;
                        Log.d("SHADER_DETECTOR","2");

                        return true;
                    }
                }


               /* if(event.getAction() == MotionEvent.ACTION_MOVE){


                    float x = event.getX(); // touch event are in dp units.
                    float y = event.getY();

                    if (x < 0.f) x = 0.f;
                    if (x > colorShaderBox.getMeasuredWidth()) x = colorShaderBox.getMeasuredWidth();
                    if (y < 0.f) y = 0.f;
                    if (y > colorShaderBox.getMeasuredHeight()) y = colorShaderBox.getMeasuredHeight();

                    setSat(1.f / colorShaderBox.getMeasuredWidth() * x);
                    setVal(1.f - (1.f / colorShaderBox.getMeasuredHeight() * y));

                    // update view
                    if(move1){
                        moveTarget();
                   //     move2 = false;
                    }

                    if(move2){
                        moveTarget2();
                     //   move1= false;
                    }



                    selectedColor.setCardBackgroundColor(getColor());

                     return true;

                }else if(event.getAction() == MotionEvent.ACTION_DOWN){

                    //ok g
                    return true;

                }else{

                    if(move1==true){
                        move1 = false;
                        move2 = true;
                    }
                    if(move2 == true ){
                        move2 = false;
                        move1 = true;
                    }
                    return true;

                }*/

                return true;
            }
        });

        dialog = new AlertDialog.Builder(context)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (CustomGradientSelectorDialog.this.listener != null) {
                            CustomGradientSelectorDialog.this.listener.onOk(CustomGradientSelectorDialog.this, color1,color2);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (CustomGradientSelectorDialog.this.listener != null) {
                            CustomGradientSelectorDialog.this.listener.onCancel(CustomGradientSelectorDialog.this);
                        }
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    // if back button is used, call back our listener.
                    @Override
                    public void onCancel(DialogInterface paramDialogInterface) {
                        if (CustomGradientSelectorDialog.this.listener != null) {
                            CustomGradientSelectorDialog.this.listener.onCancel(CustomGradientSelectorDialog.this);
                        }

                    }
                })
                .create();
        // kill all padding from the dialog window
        dialog.setView(view, 0, 0, 0, 0);

        // move cursor & target on first draw
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                moveCursor();
                moveTarget();
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    protected void moveCursor() {
        float y = colorPallet.getMeasuredHeight() - (getHue() * colorPallet.getMeasuredHeight() / 360.f);
        if (y == colorPallet.getMeasuredHeight()) y = 10.0f;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pallet_hand.getLayoutParams();
        layoutParams.leftMargin = (int) (colorPallet.getLeft() - Math.floor(pallet_hand.getMeasuredWidth() / 3f));
        layoutParams.topMargin = (int) (colorPallet.getTop() + y - Math.floor(pallet_hand.getMeasuredHeight() / 2f) - viewsContainer.getPaddingTop());
        pallet_hand.setLayoutParams(layoutParams);
    }

    protected void moveTarget() {

        float x = getSat() * colorShaderBox.getMeasuredWidth();
        float y = (1.f - getVal()) * colorShaderBox.getMeasuredHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) shader_hand1.getLayoutParams();
        layoutParams.leftMargin = (int) (colorShaderBox.getLeft() + x - Math.floor(shader_hand1.getMeasuredWidth() / 2f) - viewsContainer.getPaddingLeft());
        layoutParams.topMargin = (int) (colorShaderBox.getTop() + y - Math.floor(shader_hand1.getMeasuredHeight() / 2f) - viewsContainer.getPaddingTop());
        shader_hand1.setLayoutParams(layoutParams);

        Log.d("SHADER_HAND", "MOVING");

    }

    protected void moveTarget2() {
        float x = getSat() * colorShaderBox.getMeasuredWidth();
        float y = (1.f - getVal()) * colorShaderBox.getMeasuredHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) shader_hand2.getLayoutParams();
        layoutParams.leftMargin = (int) (colorShaderBox.getLeft() + x - Math.floor(shader_hand2.getMeasuredWidth() / 2f) - viewsContainer.getPaddingLeft());
        layoutParams.topMargin = (int) (colorShaderBox.getTop() + y - Math.floor(shader_hand2.getMeasuredHeight() / 2f) - viewsContainer.getPaddingTop());
        shader_hand2.setLayoutParams(layoutParams);

        Log.d("SHADER_HAND", "MOVING ....2");


    }





    private int getColor() {
        final int argb = Color.HSVToColor(currentColorHsv);
        return alpha << 24 | (argb & 0x00ffffff);
    }

    private String getHexColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    private float getHue() {
        return currentColorHsv[0];
    }

    private float getAlpha() {
        return this.alpha;
    }

    private float getSat() {
        return currentColorHsv[1];
    }

    private float getVal() {
        return currentColorHsv[2];
    }

    private void setHue(float hue) {
        currentColorHsv[0] = hue;
    }

    private void setSat(float sat) {
        currentColorHsv[1] = sat;
    }

    private void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    private void setVal(float val) {
        currentColorHsv[2] = val;
    }

    public void show() {
        dialog.show();
    }

    public AlertDialog getDialog() {
        return dialog;
    }

}