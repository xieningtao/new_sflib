package com.sflib.openGL.model;

import android.content.res.AssetManager;
import android.text.TextUtils;


import com.sf.loglib.L;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/11/9 0009.
 */

public class LoadMaxModelHelper {

    private final String TAG = LoadMaxModelHelper.class.getName();
    private FloatBuffer verts;
    private ByteBuffer textCoords;
    private ByteBuffer norms;
    private IntBuffer mIndBuff;
    private int numVerts = 0;
    private int numIndex = 0;
    private List<Float> mVertexFloat = new ArrayList<>();
    private List<Integer> mFragmentIndex = new ArrayList<>();

    public void loadObjModel(AssetManager assetManager, String filename) {
        try {
            InputStream is = assetManager.open(filename);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            loadVertex(reader);
            loadFragmentIndex(reader);
        } catch (Exception e) {
            L.error(TAG, "loadObjModel exception: " + e);
        }

    }

    private void addIndex(String segment) {
        if (TextUtils.isEmpty(segment)) {
            return;
        }
        String allIndex[] = segment.split("/");
        if (allIndex != null && allIndex.length >= 3) {
            mFragmentIndex.add(Integer.parseInt(allIndex[0]) - 1);
        }
    }


    private void loadFragmentIndex(BufferedReader reader) {
        try {
            String line = reader.readLine();
            int fragmentNum = 0;
            while (line != null && !line.startsWith("f")) {
                line = reader.readLine();
                if (line == null) {
                    L.info(TAG, "loadFragmentIndex file end");
                    return;
                }
            }

            String fragmentIndex[] = line.split(" ");
            while (line.startsWith("f") && fragmentIndex != null && fragmentIndex.length > 3) {
                addIndex(fragmentIndex[1]);
                fragmentNum++;
                addIndex(fragmentIndex[2]);
                fragmentNum++;
                addIndex(fragmentIndex[3]);
                fragmentNum++;
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                fragmentIndex = line.split(" ");
            }
            numIndex = fragmentNum;
            ByteBuffer indBuff = ByteBuffer.allocateDirect(fragmentNum * 4);
            indBuff.order(ByteOrder.nativeOrder());
            mIndBuff = indBuff.asIntBuffer();

            int indBuffArray[] = new int[mFragmentIndex.size()];
            for (int i = 0; i < fragmentNum; i++) {
                indBuffArray[i] = mFragmentIndex.get(i);
            }
            mIndBuff.put(indBuffArray);
            mIndBuff.position(0);

        } catch (Exception e) {
            L.error(TAG, "exception: " + e);
        }
    }

    private void loadVertex(BufferedReader reader) {
        try {
            String line = reader.readLine();
            int vertexNum = 0;
            while (line != null && !line.startsWith("v")) {
                line = reader.readLine();
                if (line == null) {
                    L.info(TAG, "loadVertex file end");
                    return;
                }
            }

            String vertexContent[] = line.split(" ");
            while (line.startsWith("v") && vertexContent != null && vertexContent.length > 3) {

                mVertexFloat.add(Float.parseFloat(vertexContent[2]));
                vertexNum++;
                mVertexFloat.add(Float.parseFloat(vertexContent[3]));
                vertexNum++;
                mVertexFloat.add(Float.parseFloat(vertexContent[4]));
                vertexNum++;
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                vertexContent = line.split(" ");
            }
            numVerts = vertexNum;
            ByteBuffer vertsBuffer = ByteBuffer.allocateDirect(vertexNum * 4);
            vertsBuffer.order(ByteOrder.nativeOrder());
            verts = vertsBuffer.asFloatBuffer();
            float vertsArray[] = new float[mVertexFloat.size()];
            for (int i = 0; i < vertexNum; i++) {
                vertsArray[i] = mVertexFloat.get(i);
            }
            verts.put(vertsArray);
            verts.position(0);
        } catch (Exception e) {
            L.error(TAG, "loadVertex exception: " + e);
        }

    }


    public Buffer getBuffer(LoadOBJModelHelper.BUFFER_TYPE bufferType) {
        Buffer result = null;
        switch (bufferType) {
            case BUFFER_TYPE_VERTEX:
                result = verts;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = textCoords;
                break;
            case BUFFER_TYPE_NORMALS:
                result = norms;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
                break;
            default:
                break;
        }
        return result;
    }


    public int getNumObjectVertex() {
        return numVerts;
    }


    public int getNumObjectIndex() {
        return numIndex;
    }
}
