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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Marwin Lebensky on 21.07.2017.
 */

public class EleWaveFront extends Ele{
    private List<String> verticesList;
    private List<String> facesList;
    private FloatBuffer verticesBuffer;
    private ShortBuffer facesBuffer;
    private Context context;
    private int program;
    private int vertexShader;
    private int fragmentShader;
    int position = GLES20.glGetAttribLocation(program, "position");
    float[] projectionMatrix = new float[16];
    float[] viewMatrix = new float[16];
    float[] productMatrix = new float[16];
    public CustomVector rot = new CustomVector();
    public CustomVector vel = new CustomVector();
    public CustomVector pos = new CustomVector();
    public CustomVector acc = new CustomVector();
    private int matrix;
    public EleWaveFront() {
        verticesList = new ArrayList<>();
        facesList = new ArrayList<>();

        this.context = Util.currentActivity;
        // Read .obj
        readData();
        initBuffers();
        populateVerticesBuffer();
        populateFacesBuffer();
        compileAndCreateShaders();
        setMatrix();

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
//        matrix = GLES20.glGetUniformLocation(program, "matrix");
//        transform(viewMatrix);
        transform(productMatrix);
        GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0);
//        GLES20.glDrawElements(GLES20.GL_LINE_LOOP,facesList.size() * 3, GLES20.GL_UNSIGNED_SHORT, facesBuffer);
        GLES20.glDisableVertexAttribArray(position);

        transform(projectionMatrix);

    }
    public void draw(){
//        transform(projectionMatrix);
        setMatrix();
        position = GLES20.glGetAttribLocation(program, "position");
        GLES20.glEnableVertexAttribArray(position);
        GLES20.glVertexAttribPointer(position,3, GLES20.GL_FLOAT, false, 3 * 4, verticesBuffer);

        GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0);
        GLES20.glLineWidth(10f);
//        randomFaces();
//        randomVertices();
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, facesList.size() * 3 , GLES20.GL_UNSIGNED_SHORT, facesBuffer);
        GLES20.glDrawElements(GLES20.GL_POINTS, facesList.size() * 3 , GLES20.GL_UNSIGNED_SHORT, facesBuffer);
        GLES20.glDisableVertexAttribArray(position);
    }
    public void randomFaces(){
        try {
            facesBuffer.position(0);
            int i = 0;
            while (facesBuffer.hasRemaining()) {
//            facesBuffer.put((short) (facesBuffer.get(i)+ (Math.random()*1)));
//            facesBuffer.put((short) (facesBuffer.get() * (Math.random()*1.2)));
//                Log.d("faces", String.valueOf(facesBuffer.get(i)));
                i++;
            }
            Log.d("counter", String.valueOf(i));
            facesBuffer.position(0);
        }catch (Exception e){

        }
    }
    public void randomVertices(){
        try {
            verticesBuffer.position(0);
            int i = 0;
            float ra = 1.03f;
            while (verticesBuffer.hasRemaining()) {
//            facesBuffer.put((short) (facesBuffer.get(i)+ (Math.random()*1)));
//                verticesBuffer.put((float) (facesBuffer.get() * (Math.random()*1.2)));
                verticesBuffer.put(verticesBuffer.get(i)+(float)(Math.random()*ra)-ra/2);
//                Log.d("vertices",String.valueOf(verticesBuffer.get()));
//                Log.d("faces", String.valueOf(facesBuffer.get(i)));
                i++;
            }
            Log.d("counter", String.valueOf(i));
            Log.d("vertices", String.valueOf(facesBuffer.get(i)));

        }catch (Exception e){
            Log.d("vertices",e.toString());
        }
        verticesBuffer.position(0);
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

    public void compileAndCreateShaders(){
        try {
            // Convert vertex_shader.txt to a string
            InputStream vertexShaderStream =
                    context.getResources().openRawResource(R.raw.vertex_shader);
            String vertexShaderCode =
                    IOUtils.toString(vertexShaderStream, Charset.defaultCharset());
            vertexShaderStream.close();
// Convert fragment_shader.txt to a string
            InputStream fragmentShaderStream =
                    context.getResources().openRawResource(R.raw.fragment_shader);
            String fragmentShaderCode =
                    IOUtils.toString(fragmentShaderStream, Charset.defaultCharset());
            fragmentShaderStream.close();
            vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
            GLES20.glShaderSource(vertexShader, vertexShaderCode);

            fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
            GLES20.glShaderSource(fragmentShader, fragmentShaderCode);
            GLES20.glCompileShader(vertexShader);
            GLES20.glCompileShader(fragmentShader);
            program = GLES20.glCreateProgram();

            GLES20.glAttachShader(program, vertexShader);
            GLES20.glAttachShader(program, fragmentShader);

            GLES20.glLinkProgram(program);
            GLES20.glUseProgram(program);
        } catch (Exception e){
            Log.d("Eerey", e.toString());
        }
    }

    public void initBuffers(){
        // Create buffer for vertices
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(verticesList.size() * 3 * 4);
        buffer1.order(ByteOrder.nativeOrder());
        verticesBuffer = buffer1.asFloatBuffer();
        // Create buffer for faces
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(facesList.size() * 3 * 2);
        buffer2.order(ByteOrder.nativeOrder());
        facesBuffer = buffer2.asShortBuffer();
    }

    public void populateVerticesBuffer(){
        for(String vertex: verticesList) {
            String coords[] = vertex.split(" "); // Split by space
            float x = Float.parseFloat(coords[1]);
            float y = Float.parseFloat(coords[2]);
            float z = Float.parseFloat(coords[3]);
            verticesBuffer.put(x);
            verticesBuffer.put(y);
            verticesBuffer.put(z);
        }
        verticesBuffer.position(0);
    }
    public void populateFacesBuffer(){
        for(String face: facesList) {
            String vertexIndices[] = face.split(" ");
            short vertex1 = Short.parseShort(vertexIndices[1]);
            short vertex2 = Short.parseShort(vertexIndices[2]);
            short vertex3 = Short.parseShort(vertexIndices[3]);
            facesBuffer.put((short)(vertex1 - 1));
            facesBuffer.put((short)(vertex2 - 1));
            facesBuffer.put((short)(vertex3 - 1));
        }
        facesBuffer.position(0);
    }


    public void readData() {
        // Open the OBJ file with a Scanner
        try {
            Scanner scanner = new Scanner(this.context.getAssets().open("arrow.obj"));
            // Loop through all its lines
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("v ")) {
                    // Add vertex line to list of vertices
                    verticesList.add(line);
                } else if (line.startsWith("f ")) {
                    // Add face line to faces list
                    facesList.add(line);
                }
            }

// Close the scanner
            scanner.close();

        } catch (Exception e) {
            Log.d("Eerey", e.toString());
        }

    }
}
