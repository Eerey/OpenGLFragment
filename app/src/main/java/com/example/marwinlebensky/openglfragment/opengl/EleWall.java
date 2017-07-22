package com.example.marwinlebensky.openglfragment.opengl;

import android.opengl.GLES20;
import android.util.Log;

import com.example.marwinlebensky.openglfragment.constants.Constants;
import com.example.marwinlebensky.openglfragment.logic.CustomVector;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Marwin Lebensky on 21.07.2017.
 */

public class EleWall extends Ele{
public CustomVector start;
public CustomVector end;
public CustomVector pos;
public float startX, startY, startZ, endX, endY, endZ;

    public EleWall(){
        this.start = new CustomVector();
        this.end = new CustomVector();
        this.start.set(-0.5f,0,0);
        this.end.set(0.5f,0,0);
        init();
    }
    public EleWall(float startX, float startY, float startZ, float endX, float endY, float endZ){
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;

        this.start = new CustomVector();
        this.end = new CustomVector();
        this.start.set(startX,startY,startZ);
        this.end.set(endX,endY,endZ);
        init();
    }
    public EleWall(float startX, float startY, float endX, float endY){
        this.start = new CustomVector();
        this.end = new CustomVector();
        this.start.set(startX,startY,0);
        this.end.set(endX,endY,0);
        init();
    }
    public void init(){
        initBuffers();
        populateVertices();
        populateFaces();
        setPointers();
    }

    public void draw(){
        try {
//            position = GLES20.glGetAttribLocation(program, "position");

//            GLES20.glVertexAttribPointer(position,3, GLES20.GL_FLOAT, false, 3 * 4, verticesBuffer);
//            GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0);
            GLES20.glVertexAttribPointer(position,3, GLES20.GL_FLOAT, false, 3 * 4, verticesBuffer);
            GLES20.glVertexAttribPointer(position,3, GLES20.GL_SHORT, false, 3 * 2, facesBuffer);
            GLES20.glEnableVertexAttribArray(position);
            GLES20.glDrawElements(GLES20.GL_POINTS, 6 * 3, GLES20.GL_UNSIGNED_SHORT, facesBuffer);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);
        }catch (Exception e){
            Log.d("Exception",e.toString());
        }
    }
    public void setPointers(){

    }
    public void populateVertices(){
//        this.start = new CustomVector();
//        this.end = new CustomVector();
//        this.start.set(-0.5f,0,0);
//        this.end.set(0.5f,0,0);
        //bottom
            verticesBuffer.put((float)startX- Constants.wallThickness/2);
            verticesBuffer.put((float)startY- Constants.wallThickness/2);
            verticesBuffer.put((float)startZ);
            verticesBuffer.put((float)endX- Constants.wallThickness/2);
            verticesBuffer.put((float)endY- Constants.wallThickness/2);
            verticesBuffer.put((float)endZ);
            verticesBuffer.put((float)endX+ Constants.wallThickness/2);
            verticesBuffer.put((float)endY+ Constants.wallThickness/2);
            verticesBuffer.put((float)endZ);
            verticesBuffer.put((float)startX+ Constants.wallThickness/2);
            verticesBuffer.put((float)startY+ Constants.wallThickness/2);
            verticesBuffer.put((float)startZ);
        //top
            verticesBuffer.put((float)startX+ Constants.wallThickness/2);
            verticesBuffer.put((float)startY+ Constants.wallThickness/2);
            verticesBuffer.put((float)startZ+ Constants.wallHeight);
            verticesBuffer.put((float)endX+ Constants.wallThickness/2);
            verticesBuffer.put((float)endY+ Constants.wallThickness/2);
            verticesBuffer.put((float)endZ+ Constants.wallHeight);
            verticesBuffer.put((float)endX- Constants.wallThickness/2);
            verticesBuffer.put((float)endY- Constants.wallThickness/2);
            verticesBuffer.put((float)endZ+ Constants.wallHeight);
            verticesBuffer.put((float)startX- Constants.wallThickness/2);
            verticesBuffer.put((float)startY- Constants.wallThickness/2);
            verticesBuffer.put((float)startZ+ Constants.wallHeight);

        verticesBuffer.position(0);
//        verticesBuffer.rewind();
    }
    public void populateFaces(){
        facesBuffer.put((short)0);
        facesBuffer.put((short)1);
        facesBuffer.put((short)2);

        facesBuffer.put((short)0);
        facesBuffer.put((short)2);
        facesBuffer.put((short)3);

        facesBuffer.put((short)4);
        facesBuffer.put((short)5);
        facesBuffer.put((short)6);

        facesBuffer.put((short)4);
        facesBuffer.put((short)6);
        facesBuffer.put((short)7);

        facesBuffer.put((short)0);
        facesBuffer.put((short)5);
        facesBuffer.put((short)6);

        facesBuffer.put((short)3);
        facesBuffer.put((short)4);
        facesBuffer.put((short)7);
        facesBuffer.position(0);

    }
    public void initBuffers() {
        // Create buffer for vertices
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(8 * 3 * 4);
        buffer1.order(ByteOrder.nativeOrder());
        verticesBuffer = buffer1.asFloatBuffer();
        // Create buffer for faces
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(6 * 3 * 2);
        buffer2.order(ByteOrder.nativeOrder());
        facesBuffer = buffer2.asShortBuffer();
    }

}
