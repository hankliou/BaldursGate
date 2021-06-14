package com.example.baldursgate;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    public JoystickListener joystickCallback;

    // 設定初始值
    private void setupDimensions(){
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = Math.min(getWidth(), getHeight()) / 3;
        hatRadius = Math.min(getWidth(), getHeight()) / 6;
    }

    // 建構值
    public JoystickView(Context context){
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);

        if(context instanceof JoystickListener)
            joystickCallback = (JoystickListener) context;
    }

    // 建構值
    public JoystickView(Context context, AttributeSet attributes, int style){
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);

        if(context instanceof JoystickListener)
            joystickCallback = (JoystickListener) context;
    }

    // 建構值
    public JoystickView(Context context, AttributeSet attributes){
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        // 背景透明
        //this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);

        if(context instanceof JoystickListener)
            joystickCallback = (JoystickListener) context;
    }

    // 繪製 & 刷新畫面
    private void drawJoystick(float newX, float newY){

        if(getHolder().getSurface().isValid()){

            Canvas myCanvas = this.getHolder().lockCanvas(); // draw staff
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // clear BG
            colors.setARGB(200, 192, 192, 192); // Joystick base color
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors); // Draw joystick base
            colors.setARGB(200, 128, 138, 135); // stick color
            myCanvas.drawCircle(newX, newY, hatRadius, colors); // draw stick
            getHolder().unlockCanvasAndPost(myCanvas); // write the new drawing to the SurfaceView
        }
    }

    // 依規定必須覆寫
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        setupDimensions();
        drawJoystick(centerX, centerY);
    }
    // 依規定必須覆寫
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    // 依規定必須覆寫
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    // 覆寫觸碰事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 如果觸碰點是在這個view上才執行
        if(v.equals(this)){
            // 如果手沒放開才執行
            if(event.getAction() != event.ACTION_UP){
                float distance = (float)Math.sqrt(Math.pow(event.getX() - centerX, 2) + Math.pow(event.getY() - centerY, 2));
                // 如果觸碰點小於半徑
                if(distance < baseRadius){
                    drawJoystick(event.getX(), event.getY());
                    joystickCallback.onJoystickMoved((event.getX() - centerX)/baseRadius, (event.getY() - centerY)/baseRadius, getId());
                }
                // 超過半徑
                else{
                    float ratio = baseRadius / distance;
                    float constraintX = centerX + (event.getX() - centerX) * ratio;
                    float constraintY = centerY + (event.getY() - centerY) * ratio;
                    drawJoystick(constraintX, constraintY);
                    joystickCallback.onJoystickMoved((constraintX-centerX)/baseRadius, (constraintY-centerY)/baseRadius, getId());
                }
            }
            // 放開就彈回圓心
            else {
                drawJoystick(centerX, centerY);
                joystickCallback.onJoystickMoved(0, 0, getId());
            }
        }
        // 如果return false會阻止偵測接下來的觸摸
        return true;
    }

    // 對外界面
    public interface JoystickListener {
        // 取得座標的函式
        void onJoystickMoved(float xPercent, float yPercent, int id);
    }
}