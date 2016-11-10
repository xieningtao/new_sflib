package com.sflib.openGL;

/**
 * Created by xieningtao on 16-3-27.
 */
public class ShaderCode {
    //    private final String mVertexShader =
//            "uniform mat4 vmMatrix;" +
//                    "attribute vec4 position;" +
//                    "void main() {" +
//                    " gl_Position=vmMatrix*position;" +
//                    "}";
//    private final String mFragmentShader =
//            "precision mediump float;" +
//                    "uniform vec4 color;" +
//                    "void main {" +
//                    " gl_FragColor=color;" +
//                    "}";
//
    public static final String mVertexShader =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 mMatrix;" +
                    " attribute vec3 position;" +
                    " attribute vec2 aTextureCoordinate;" +
                    " varying vec2 vTextureCoordinate;" +
                    " void main() {" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = mMatrix * vec4(position,1); " +
                    "  vTextureCoordinate = aTextureCoordinate; " +
                    "}";
    public static final String mVertexObjShader =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 mMatrix;" +
                    " attribute vec3 position;" +
                    " void main() {" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = mMatrix * vec4(position,1); " +
                    "}";
    public static final String mFragmentShader =
            "precision highp float;" +
                    " varying vec2 vTextureCoordinate;" +
                    " uniform sampler2D sTexture;" +
                    " void main() {" +
                    "  gl_FragColor = texture2D(sTexture,vTextureCoordinate);" +
                    " }";
    public static final String mFragmentObjShader =
            "precision highp float;" +
                    " void main() {" +
                    "  gl_FragColor = vec4(0.8,0.3,0.5,0.5);" +
                    " }";


}
