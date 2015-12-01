package com.sf.yysdkdemo.videoplayer.shader;

public class ShaderCode {
    protected static final String VSH_CODE =
            "attribute vec4 position_vertex;" +
                    "attribute vec2 texture_vertex;" +
                    "varying vec2 texture_coord;" +
                    "uniform mat4 projection_matrix;" +
                    "uniform mat4 view_matrix;" +
                    "uniform mat4 model_matrix;" +
                    "void main() {" +
                    "gl_Position = projection_matrix * view_matrix *  model_matrix * position_vertex;" +
                    "texture_coord = texture_vertex;" +
                    "}";

    protected static final String FSH_CODE =
                    "precision highp float;" +
                    "varying vec2 texture_coord;" +
                    "uniform sampler2D texture_sampler;" +
                    "void main() {" +
                    "gl_FragColor = texture2D(texture_sampler, texture_coord);" +
                    "}";

    protected static final String POSITION = "position_vertex";
    protected static final String TEXTURE = "texture_vertex";
    protected static final String PROJECTION_MATRIX ="projection_matrix";
    protected static final String VIEW_MATRIX ="view_matrix";
    protected static final String MODEL_MATRIX ="model_matrix";
    protected static final String SAMPLER = "texture_sampler";
}
