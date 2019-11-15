package com.example.usandoacelerometro;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvValorX;
    private TextView tvValorY;
    private TextView tvValorZ;
    private TextView valorMaior;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean runnig;

    private float dadoMax = 0;

    float dadoX, dadoY, dadoZ;

    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    private float t = 5.0f;

    private float[] mGravity = {0.0f, 0.0f, 0.0f};
    private float[] mLinearAcceleration = {0.0f, 0.0f, 0.0f};

    private SensorManager mSensorManager;
    private Sensor mAcelerometro;

    private Acelerometro acelerometro = new Acelerometro();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvValorX = findViewById(R.id.tvValorX);
        tvValorY = findViewById(R.id.tvValorY);
        tvValorZ = findViewById(R.id.tvValorZ);
        valorMaior = findViewById(R.id.valorMaior);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(MainActivity.this, "Bing!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        NumberFormat formatarFloat = new DecimalFormat("0.0");

        final float alpha = 0.8f;

        // Gravity components of x, y, and z acceleration
        mGravity[X] = alpha * mGravity[X] + (1 - alpha) * event.values[X];
        mGravity[Y] = alpha * mGravity[Y] + (1 - alpha) * event.values[Y];
        mGravity[Z] = alpha * mGravity[Z] + (1 - alpha) * event.values[Z];

        // Linear acceleration along the x, y, and z axes (gravity effects removed)
        mLinearAcceleration[X] = event.values[X] - mGravity[X];
        mLinearAcceleration[Y] = event.values[Y] - mGravity[Y];
        mLinearAcceleration[Z] = event.values[Z] - mGravity[Z];

        // variáveis para recebr os valores do sensores
        dadoX = abs(mLinearAcceleration[X]);
        dadoY = abs(mLinearAcceleration[Y]);
        dadoZ = abs(mLinearAcceleration[Z]);

        acelerometro.setEixoX(dadoX*10);
        acelerometro.setEixoY(dadoY*10);

        // tirar as casas decimais
        String eixoXformtat = formatarFloat.format(acelerometro.getEixoX());
        String eixoYformtat = formatarFloat.format(acelerometro.getEixoY());
        String eixoZformtat = formatarFloat.format(acelerometro.getEixoZ());

        // coloca os valores dos sensores dentro das Text
        tvValorX.setText(String.valueOf(eixoXformtat));
        tvValorY.setText(String.valueOf(eixoYformtat));
        tvValorZ.setText(String.valueOf(eixoZformtat));

        float dadoNovo = acelerometro.getEixoX();

        if (dadoNovo >= dadoMax) {
            dadoMax = dadoNovo;
            if (dadoMax > 10.0f && dadoMax < 50.0f) {
                valorMaior.setText(String.valueOf(dadoMax));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startChronometer(View view) {

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAcelerometro = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAcelerometro, SensorManager.SENSOR_DELAY_UI);

        if (!runnig){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            runnig = true;


        }
    }

    public void pauseChronometer(View view) {
        if (runnig){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            runnig = false;

            mSensorManager.unregisterListener(this);
        }
    }

    public void resetChronometer(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void calculaMaior (View view){

        if (acelerometro.getEixoX() > t) {
            Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show();
        }
    }
}