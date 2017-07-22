package com.example.marwinlebensky.openglfragment.logic;

import android.util.Log;

import com.example.marwinlebensky.openglfragment.enums.State;

import java.util.ArrayList;

import static com.example.marwinlebensky.openglfragment.enums.State.WAITING;


/**
 * Created by Marwin Lebensky on 07.07.2017.
 */

public class VectorManager {

    public static CustomVector pos;
    public static CustomVector vel;
    public static CustomVector lastVel;
    public static CustomVector acc;
    public static CustomVector calibration;
    public static CustomVector rot;
    public int CALIBRATION_THRESHOLD = 200;
    public int MOVEMENT_THRESHOLD = 200;
    public double ACCELERATION_THRESHOLD = 2;
    public State state;
    public boolean accelerating = false;
    public boolean decelerating = false;

    public  ArrayList<CustomVector> calibrationList = new ArrayList<CustomVector>();
    private static VectorManager instance;
    public static VectorManager getInstance(){
        if (VectorManager.instance == null){
            VectorManager.instance = new VectorManager();

        }
        return VectorManager.instance;
    }

    private VectorManager(){
        pos = new CustomVector();
        vel = new CustomVector();
        lastVel = new CustomVector();
        acc = new CustomVector();
        calibration = new CustomVector();
        rot = new CustomVector();
//        state = State.WAITING;
    }
    public void reset(){
        accelerating = true;
        decelerating = false;
        acc.set(0,0,0);
        vel.set(0,0,0);
        pos.set(0,0,0);
    }
    public void update(double newAccX, double newAccY, double newAccZ){
        acc.set((float)newAccX,(float)newAccY,(float)newAccZ);
        vel.add(acc);
        pos.add(vel);

    }
    public void checkIfMoving(){

    }
    public void setState(State state){

    }

    public CustomVector averageCalibration(){
        if (calibrationList.size()<CALIBRATION_THRESHOLD & calibrationList.size()>0) {
            int i = 0;
            CustomVector avg = new CustomVector();
            for (CustomVector v : calibrationList) {
                avg.add(v);
            }
            calibration = avg;
        } else{

        }
        return calibration;
    }

    public CustomVector getCalibration() {
        return calibration;
    }

    public void addCalibrationElement(CustomVector v){
        if (calibrationList.size()<CALIBRATION_THRESHOLD) calibrationList.add(v);
    }

    public void setCalibration(CustomVector calibartion) {
        this.calibration = calibartion;
    }
    public CustomVector getPos() {
        return pos;
    }

    public void setPos(CustomVector pos) {
        this.pos = pos;
    }

    public CustomVector getVel() {
        return vel;
    }

    public void setVel(CustomVector vel) {
        this.vel = vel;
    }

    public CustomVector getAcc() {
        return acc;
    }

    public void setAcc(CustomVector acc) {
        this.acc = acc;
    }

    public CustomVector getLastVel() {
        return lastVel;
    }

    public void setLastVel(CustomVector lastVel) {
        this.lastVel = lastVel;
    }



}
