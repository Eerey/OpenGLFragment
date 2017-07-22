package com.example.marwinlebensky.openglfragment;

import android.app.Activity;
import android.opengl.GLSurfaceView;

/**
 * Created by Marwin Lebensky on 14.07.2017.
 */

class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Activity context){
        super(context);
        setEGLContextClientVersion(2);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new MyGLRenderer());

    }
}