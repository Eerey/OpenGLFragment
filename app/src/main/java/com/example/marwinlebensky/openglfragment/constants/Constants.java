package com.example.marwinlebensky.openglfragment.constants;

import android.content.Context;

/**
 * Created by Marwin Lebensky on 21.07.2017.
 */

public class Constants {

    public static Context context;

    public static float frustumConstant = 0.5f;
    public static float frustumLeft = -frustumConstant;
    public static float frustumRight = frustumConstant;
    public static float frustumBottom = -frustumConstant;
    public static float frustumTop = frustumConstant;
    public static float frustumNear = 1f;
    public static float frustumFar = 50f;

    public static float wallHeight = 1f;
    public static float wallThickness = 0.3f;

    public static int program = 0;
    public static int vertexShader = 0;
    public static int fragmentShader = 0;
}
