package com.example.marwinlebensky.openglfragment.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.example.marwinlebensky.openglfragment.R;
import com.example.marwinlebensky.openglfragment.Util;
import com.example.marwinlebensky.openglfragment.constants.Constants;
import com.example.marwinlebensky.openglfragment.logic.CustomVector;
import com.example.marwinlebensky.openglfragment.logic.VectorManager;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marwin Lebensky on 21.07.2017.
 */

public class World {
    ArrayList<Ele> elements;
    private List<String> verticesList;
    private List<String> facesList;
    private FloatBuffer verticesBuffer;
    private ShortBuffer facesBuffer;
    private Context context;
    private int program;
    int position = GLES20.glGetAttribLocation(program, "position");
    float[] projectionMatrix = new float[16];
    float[] viewMatrix = new float[16];
    float[] productMatrix = new float[16];
    public CustomVector rot = new CustomVector();
    public CustomVector vel = new CustomVector();
    public CustomVector pos = new CustomVector();
    public CustomVector acc = new CustomVector();
    public Arrow arrow;
    private int matrix;
    private int vertexShader;
    private int fragmentShader;

    public World(){
        init();
    }
    public void init(){
        this.context = Util.currentActivity;
        elements = new ArrayList<Ele>();
        compileAndCreateShaders();
        setMatrix();
        testElements();

    }
    public void testElements(){
        elements.add(new EleWall(-1,0,0,1,0,0));
//        elements.add(new EleWall(1,0,0,2,0,0));
//        elements.add(new EleWall(1,0,0,2,0,0));
//        arrow = new Arrow();
//        elements.add(arrow);
        elements.add(new EleWaveFront());
    }
    public void draw(){

        position = GLES20.glGetAttribLocation(program,"position");
        GLES20.glEnableVertexAttribArray(position);
        GLES20.glUseProgram(program);
        setMatrix();
        transform(productMatrix);
        transform(projectionMatrix);
//        arrow.draw();
        for (Ele e : elements){

//            e.program = this.program;
//            e.position = this.position;
            e.position = this.position;
            e.program = this.program;
            e.draw();

//            GLES20.glDrawElements(GLES20.GL_POINTS, e.getAmountFaces() * 3 , GLES20.GL_UNSIGNED_SHORT, e.facesBuffer);
//            Log.d("Eerey",String.valueOf(e.program));
            if (e.amountFaces > 0) Log.d("Eerey",String.valueOf(e.getAmountFaces()));
//            Log.d("Eerey",String.valueOf(program));
        }
        GLES20.glDisableVertexAttribArray(position);
    }
    public void transform(float[] mModelMatrix) {
//        Matrix.setIdentityM(mModelMatrix, 0);
//        Matrix.translateM(mModelMatrix, 0, 0, y, 0);
        rot.set(VectorManager.rot.x,VectorManager.rot.y,VectorManager.rot.z);
        if (rot.x < 0) rot.x +=360;
        if (rot.y < 0) rot.y +=360;
        if (rot.z < 0) rot.z +=360;
//        Matrix.setRotateEulerM();
        Matrix.rotateM(mModelMatrix, 0, (float)rot.x+180,(float)rot.x, 0,0);
        Matrix.rotateM(mModelMatrix, 0, (float)rot.y,0, 1f,0);
//        Matrix.rotateM(mModelMatrix, 0, (float)rot.z,0,0, 1f);
//        Log.d("Rotation",String.valueOf(rot.y));
//        Matrix.setRotateM()
//        Matrix.rotateM()
//        Log.d("Eerey",String.valueOf(VectorManager.acc.x));
//        Matrix.rotateM(mModelMatrix, 0, (float)Math.random()*3, (float)Math.random()*3, (float)Math.random()*3, (float)Math.random()*3);
//        Matrix.translateM(mModelMatrix,0,mModelMatrix,0,(float)Math.random()*3,(float)Math.random()*3,(float)Math.random()*3);
    }

    public void setMatrix(){

        Matrix.frustumM(projectionMatrix, 0,
                Constants.frustumLeft, Constants.frustumRight, // left,right
                Constants.frustumBottom, Constants.frustumTop, // bottom, top
                Constants.frustumNear, Constants.frustumFar); // near, far
        Matrix.setLookAtM(viewMatrix, 0,
                0, 3, -5,
                0, 0, 0,
                0, 1, 0);
        Matrix.multiplyMM(productMatrix, 0,
                projectionMatrix, 0,
                viewMatrix, 0);
        matrix = GLES20.glGetUniformLocation(program, "matrix");
        GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0);


//        transform(projectionMatrix);

    }
    public void compileAndCreateShaders(){
        try {
            //GL SL like c but some extensions
            String vertexShaderString = "" +
                    "" +
                    "attribute vec4 position;" + // attribute = some set of data that's being passed in per vertex that fills in this variable, will contain EACH ROW of Datasets (vertices)
                    "void main()" +
                    "{" +
                    "   gl_Position = matrix * position;" + // take in input and DIRECTLY output the data
                    "}" +
                    "" +
                    ""
                    ;
            // Convert vertex_shader.txt to a string  ## alternative
            InputStream vertexShaderStream = context.getResources().openRawResource(R.raw.vertex_shader);
//            vertexShaderString = IOUtils.toString(vertexShaderStream, Charset.defaultCharset());
            vertexShaderStream.close();


            String fragmentShaderString = "" +
                    "" +
                    "void main()" +
                    "{" +
                    "   gl_FragColor = vec4(0.8, 0.7, 0.6, 1.0);" +
                    "}" +
                    "" +
                    ""
                    ;
            // Convert fragment_shader.txt to a string ## alternative
            InputStream fragmentShaderStream = context.getResources().openRawResource(R.raw.fragment_shader);
//            fragmentShaderString = IOUtils.toString(fragmentShaderStream, Charset.defaultCharset());
            fragmentShaderStream.close();

            vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
            GLES20.glShaderSource(vertexShader, vertexShaderString);
            GLES20.glCompileShader(vertexShader);

            fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
            GLES20.glShaderSource(fragmentShader, fragmentShaderString);
            GLES20.glCompileShader(fragmentShader);

            program = GLES20.glCreateProgram(); //Programs can have multiple Shaders. These Shaders work together in a program!
            // https://www.youtube.com/watch?v=oED0msC6O0Y&t=1851s
            // Depending on what you're drawing you use different programs. Generally multiple programs are in a single OpenGL App
            GLES20.glAttachShader(program, vertexShader);
            GLES20.glAttachShader(program, fragmentShader);
            GLES20.glBindAttribLocation(program, 0, "position"); // index of glVertexAttribPointer ; "position" = variable in ShaderSource, connected to VertexArray
            GLES20.glLinkProgram(program); // Tell the Program to link
            Log.d("GLES",GLES20.glGetProgramInfoLog(program)); // In case of link errors
            GLES20.glUseProgram(program); // you can call it any time you want, Set the current program!

        } catch (Exception e){
            Log.d("Eerey", e.toString());
        }
    }
}
