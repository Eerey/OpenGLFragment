package com.example.marwinlebensky.openglfragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marwinlebensky.openglfragment.logic.VectorManager;

import java.text.DecimalFormat;


public class OpenGLFragment extends Fragment implements SensorEventListener
{
    private GLSurfaceView mGLView;
    public VectorManager vm;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senRotation;
    private Sensor senGravity;
    long lastUpdate;
    float last_x, last_y, last_z;
    private float azimuth, roll, pitch;
    float[] rotationMatrix;
    private DecimalFormat df = new DecimalFormat("000");
    private DecimalFormat df_linAcc = new DecimalFormat("0.00");

    public OpenGLFragment()
    {
        super();
    }
    public void setSensors(){
        senSensorManager = (SensorManager) Util.currentActivity.getSystemService(Context.SENSOR_SERVICE);
        senRotation = senSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        senGravity = senSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        senSensorManager.registerListener(this, senRotation , SensorManager.SENSOR_DELAY_FASTEST);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_FASTEST);
        senSensorManager.registerListener(this, senGravity , SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mGLView = new MyGLSurfaceView(this.getActivity()); //I believe you may also use getActivity().getApplicationContext();
        setSensors();
        vm = VectorManager.getInstance();
        return mGLView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            try {
                rotationMatrix=new float[16];
                senSensorManager.getRotationMatrixFromVector(rotationMatrix,event.values);
                determineOrientation(rotationMatrix);
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                long curTime = System.currentTimeMillis();

                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
//                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

//                vm.rot.set((x<0?180+x:x), (y<0?180+y:y), (z<0?180+z:z));
                ((TextView) (getActivity().findViewById(R.id.orientation))).setText(
                        "Pitch"   + System.getProperty ("line.separator")    + df.format(pitch)   + System.getProperty ("line.separator") +
                        "Azimuth" + System.getProperty ("line.separator")    + df.format(azimuth) + System.getProperty ("line.separator") +
                        "Roll"    + System.getProperty ("line.separator")    + df.format(roll)


                );
                vm.rot.set(pitch,azimuth,roll);
//                vm.rot.set(azimuth,pitch,roll);

            }catch (Exception e){}
        }
        if (mySensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            try {
                rotationMatrix=new float[16];
                senSensorManager.getRotationMatrixFromVector(rotationMatrix,event.values);
                determineOrientation(rotationMatrix);
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                long curTime = System.currentTimeMillis();

                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                vm.update(x, y, z);
                ((TextView) (getActivity().findViewById(R.id.sensor_linear_acc))).setText(
                        "X" + System.getProperty ("line.separator") + df_linAcc.format(x) + System.getProperty ("line.separator") +
                                "Y" + System.getProperty ("line.separator") + df_linAcc.format(y) + System.getProperty ("line.separator") +
                                "Z" + System.getProperty ("line.separator") + df_linAcc.format(z) + System.getProperty ("line.separator") +
                                "L" + System.getProperty ("line.separator") + df_linAcc.format(vm.getVel().length()));
                ((TextView) (getActivity().findViewById(R.id.velocity))).setText(
                        "X" + System.getProperty ("line.separator") + df_linAcc.format(vm.getVel().x) + System.getProperty ("line.separator") +
                                "Y" + System.getProperty ("line.separator") + df_linAcc.format(vm.getVel().y) + System.getProperty ("line.separator") +
                                "Z" + System.getProperty ("line.separator") + df_linAcc.format(vm.getVel().z) + System.getProperty ("line.separator") +
                                "L" + System.getProperty ("line.separator") + df_linAcc.format(vm.getVel().length()));
                ((TextView) (getActivity().findViewById(R.id.position))).setText(
                        "X" + System.getProperty ("line.separator") + df_linAcc.format(vm.getPos().x) + System.getProperty ("line.separator") +
                                "Y" + System.getProperty ("line.separator") + df_linAcc.format(vm.getPos().y) + System.getProperty ("line.separator") +
                                "Z" + System.getProperty ("line.separator") + df_linAcc.format(vm.getPos().z) + System.getProperty ("line.separator") +
                                "L" + System.getProperty ("line.separator") + df_linAcc.format(vm.getPos().length()));
            }catch (Exception e){}
        }

        if (mySensor.getType() == Sensor.TYPE_GRAVITY) {
            try {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                ((TextView) (getActivity().findViewById(R.id.sensor_acc))).setText(
                        "X" + System.getProperty ("line.separator") + df_linAcc.format(vm.getAcc().x) + System.getProperty ("line.separator") +
                                "Y" + System.getProperty ("line.separator") + df_linAcc.format(vm.getAcc().y) + System.getProperty ("line.separator") +
                                "Z" + System.getProperty ("line.separator") + df_linAcc.format(vm.getAcc().z) + System.getProperty ("line.separator") +
                                "L" + System.getProperty ("line.separator") + df_linAcc.format(vm.getAcc().length()));

            }catch (Exception e){ Log.d("exception in gravity",e.toString());}
        }
    }
    private void determineOrientation(float[] rotationMatrix)
    {
        float[] orientationValues = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationValues);
        azimuth =(float) -Math.toDegrees(orientationValues[0]);
        pitch =(float) -Math.toDegrees(orientationValues[1]);
        roll =(float) -Math.toDegrees(orientationValues[2]);
//        if (orientationValues[0] < 0) azimuth = -azimuth;
//        if (orientationValues[1] < 0) pitch = -pitch;
//        if (orientationValues[2] < 0) roll = -roll;


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

