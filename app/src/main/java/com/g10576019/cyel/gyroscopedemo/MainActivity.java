package com.g10576019.cyel.gyroscopedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.g10576019.cyel.gyroscopedemo.sensors.GyroscopeManager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GyroscopeManager.GyroscopeListener {
    private GyroscopeManager gyroscopeManager;
    private TextView axisXTextView, axisYTextView, axisZTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyroscopeManager = new GyroscopeManager(this);

        axisXTextView = (TextView) findViewById(R.id.axisX);
        axisYTextView = (TextView) findViewById(R.id.axisY);
        axisZTextView = (TextView) findViewById(R.id.axisZ);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (gyroscopeManager.getState() == GyroscopeManager.STATE_IDLE) {
            gyroscopeManager.start(this);
        }
    }

    @Override
    protected void onStop() {
        if (gyroscopeManager.getState() == GyroscopeManager.STATE_RUNNING) {
            gyroscopeManager.stop();
        }

        super.onStop();
    }

    @Override
    public void onAngularSpeedChanged(float axisX, float axisY, float axisZ) {
        axisXTextView.setText(String.format(Locale.getDefault(), "%02.2f", axisX));
        axisYTextView.setText(String.format(Locale.getDefault(), "%02.2f", axisY));
        axisZTextView.setText(String.format(Locale.getDefault(), "%02.2f", axisZ));
    }
}
