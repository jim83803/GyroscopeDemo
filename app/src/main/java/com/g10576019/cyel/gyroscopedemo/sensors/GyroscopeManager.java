package com.g10576019.cyel.gyroscopedemo.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Jim on 2017/6/2.
 */

public class GyroscopeManager implements SensorEventListener{
    public interface GyroscopeListener {
        void onAngularSpeedChanged(float axisX, float axisY, float axisZ);
    }

    //常數
    public static final int STATE_IDLE = 1;
    public static final int STATE_RUNNING = 2;

    //變數
    private int state = STATE_IDLE;

    //物件
    private SensorManager sensorManager;
    private GyroscopeListener gyroscopeListener;

    //建構子
    public GyroscopeManager(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Log.i(this.getClass().getName(),"取得SensorManager成功");
        } else {
            Log.e(this.getClass().getName(),"取得SensorManager失敗");
        }
    }

    //公開方法
    public void start(GyroscopeListener gyroscopeListener) {
        this.gyroscopeListener = gyroscopeListener;
        if (state == STATE_IDLE) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            if (sensor != null) {
                Log.i(this.getClass().getName(),"取得GyroscopeSensor成功");
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            } else {
                Log.e(this.getClass().getName(),"取得GyroscopeSensor失敗");
                return;
            }
        }
        state = STATE_RUNNING;
        Log.i(this.getClass().getName(),"開始");
    }

    public void stop() {
        if (state == STATE_RUNNING) {
            sensorManager.unregisterListener(this);
        }
        state = STATE_IDLE;
        Log.i(this.getClass().getName(),"結束");
    }

    //Getters
    public int getState() {
        return state;
    }

    //接口實作
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (gyroscopeListener != null) {
            gyroscopeListener.onAngularSpeedChanged(event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
