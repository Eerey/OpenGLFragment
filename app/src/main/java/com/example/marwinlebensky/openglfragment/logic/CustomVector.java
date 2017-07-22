package com.example.marwinlebensky.openglfragment.logic;


/**
 * Created by Marwin Lebensky on 07.07.2017.
 */

public class CustomVector {

    public float x;
    public float y;
    public float z;

    public CustomVector(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public CustomVector(CustomVector v){
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }
    public CustomVector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(CustomVector v){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    public boolean equals(CustomVector v1){
        return (this.x == v1.x && this.y == v1.y && this.z == v1.z);
    }

    public CustomVector add(CustomVector v2){
        this.x += v2.x;
        this.y += v2.y;
        this.z += v2.z;
        return this;
    }
    public CustomVector add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    public CustomVector add(int x, int y, int z){
        this.x += (float)x;
        this.y += (float)y;
        this.z += (float)z;
        return this;
    }
    public CustomVector sub(CustomVector v2){
        this.x -= v2.x;
        this.y -= v2.y;
        this.z -= v2.z;
        return this;
    }
    public CustomVector mul(int scalar){
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public CustomVector nor(){
        return new CustomVector(x/this.length(),y/this.length(),z/this.length());
    }
    public float length(){
        return (float)Math.sqrt(x*x+y*y+z*z);
    }

    public CustomVector cpy(){
        return new CustomVector(this);
    }
    @Override
    public String toString(){
        return ("x: " + this.x + " y: " + this.y + " z: " + this.z);
    }
    public String str(){
        return ("x: " + this.x + " y: " + this.y + " z: " + this.z);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }



}
