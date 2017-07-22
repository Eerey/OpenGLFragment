package com.example.marwinlebensky.openglfragment;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.marwinlebensky.openglfragment.opengl.Arrow;
import com.example.marwinlebensky.openglfragment.opengl.EleWall;
import com.example.marwinlebensky.openglfragment.opengl.Triangle;
import com.example.marwinlebensky.openglfragment.opengl.World;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Marwin Lebensky on 14.07.2017.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {
public Triangle triangle;
public Arrow arrow;
    public World world;
    public EleWall wall;
    private Context context;
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.5f, 0.1f, 0.4f, 1.0f);
//        triangle = new Triangle();
//        arrow = new Arrow();
//        wall = new EleWall(0,0,0,2,0,0);
        world = new World();
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        GLES20.glClearColor(0.5f, 0.1f, 0.4f, 1.0f);
//        triangle.draw();
//        arrow.draw();
//        wall.draw();
        world.draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
    public static int loadShader(int type,String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader,shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}