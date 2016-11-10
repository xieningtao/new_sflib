package com.sflib.openGL;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Build;
import android.util.Log;

/**
 * Created by xieningtao on 15-10-31.
 */
public class ShaderHelper {
    private static final String TAG = ShaderHelper.class.getName();

    /**
     * Utility method for compiling a OpenGL shader.
     * <p>
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type       - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     * <p>
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static int bitmap2Texture(Bitmap bitmap) {
        int textureId[] = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
//        GLES20.glActiveTexture(GLES20.GL_TEXTURE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        checkGlError("texImage2D");
        bitmap.recycle();
        return textureId[0];
    }

    public static int createProgram() {
        //-----prepare shader program
        int program = GLES20.glCreateProgram();
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, ShaderCode.mVertexShader);
        checkGlError("loadVertexShader");
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, ShaderCode.mFragmentShader);
        String fragmentShaderLogs = GLES20.glGetShaderInfoLog(fragmentShader);
        Log.e(TAG, "fragmentShaderLogs: " + fragmentShaderLogs);
        checkGlError("loadFragmentShader");
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        String linkLogs = GLES20.glGetProgramInfoLog(program);
        Log.e(TAG, "linkLogs: " + linkLogs);
        checkGlError("linkProgram");
        return program;
        //-----edn shader
    }
    public static int createObjProgram() {
        //-----prepare shader program
        int program = GLES20.glCreateProgram();
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, ShaderCode.mVertexObjShader);
        checkGlError("loadVertexShader");
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, ShaderCode.mFragmentObjShader);
        String fragmentShaderLogs = GLES20.glGetShaderInfoLog(fragmentShader);
        Log.e(TAG, "fragmentShaderLogs: " + fragmentShaderLogs);
        checkGlError("loadFragmentShader");
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        String linkLogs = GLES20.glGetProgramInfoLog(program);
        Log.e(TAG, "linkLogs: " + linkLogs);
        checkGlError("linkProgram");
        return program;
        //-----edn shader
    }
}
