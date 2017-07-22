package com.example.marwinlebensky.openglfragment.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Marwin Lebensky on 21.07.2017.
 */

public abstract class Ele {
    public FloatBuffer verticesBuffer;
    public ShortBuffer facesBuffer;
    public int amountVertices = 0;
    public int amountFaces = 0;
    public int program;
    public int position;

    public Ele(){
        init();
    }

    public void draw(){

    }
    public void init(){

    }
    public void initBuffers(){

    }
    public int getAmountFaces(){
        if (amountFaces >0) return amountFaces;
        else {
            if (facesBuffer == null) return -1;
            facesBuffer.position(0);
            int i = 0;
            while (facesBuffer.hasRemaining()){
                facesBuffer.get();
                i++;
            }
            return i;
        }
    }

    public int getAmountVertices(){
        if (amountVertices >0) return amountVertices;
        else {
            if (facesBuffer == null) return -1;
            verticesBuffer.position(0);
            int i = 0;
            while (verticesBuffer.hasRemaining()){
                verticesBuffer.get();
                i++;
            }
            return i;
        }
    }


}
