package com.sflib.openGL.model;

import android.content.res.AssetManager;
import android.text.TextUtils;

import com.nostra13.universalimageloader.utils.L;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/11/9 0009.
 */

public class LoadMaxModelHelper {

    private final String TAG = LoadMaxModelHelper.class.getName();
    private ByteBuffer verts;
    private ByteBuffer textCoords;
    private ByteBuffer norms;
    private ByteBuffer mIndBuff;
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
            L.e(TAG, "loadObjModel exception: " + e);
        }

    }

    private void addIndex(String segment) {
        if (TextUtils.isEmpty(segment)) {
            return;
        }
        String allIndex[] = segment.split("/");
        if (allIndex != null && allIndex.length >= 3) {
            mFragmentIndex.add(Integer.parseInt(allIndex[0]));
        }
    }


    private void loadFragmentIndex(BufferedReader reader) {
        try {
            String line = reader.readLine();
            int fragmentNum = 0;
            while (line != null && !line.startsWith("f")) {
                line = reader.readLine();
                if (line == null) {
                    L.i(TAG, "loadFragmentIndex file end");
                    return;
                }
            }

            String fragmentIndex[] = line.split(" ");
            while (line.startsWith("f") && fragmentIndex != null && fragmentIndex.length > 3) {
                fragmentNum++;
                addIndex(fragmentIndex[1]);
                addIndex(fragmentIndex[2]);
                addIndex(fragmentIndex[3]);
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                fragmentIndex = line.split(" ");
            }
            numIndex = fragmentNum;
            mIndBuff = ByteBuffer.allocateDirect(fragmentNum * 4);
            mIndBuff.order(ByteOrder.nativeOrder());
            for (int i = 0; i < fragmentNum; i++) {
                mIndBuff.putInt(mFragmentIndex.get(i));
            }
            mIndBuff.position(0);

        } catch (Exception e) {

        }
    }

    private void loadVertex(BufferedReader reader) {
        try {
            String line = reader.readLine();
            int vertexNum = 0;
            while (line != null && !line.startsWith("v")) {
                line = reader.readLine();
                if (line == null) {
                    L.i(TAG, "loadVertex file end");
                    return;
                }
            }

            String vertexContent[] = line.split(" ");
            while (line.startsWith("v") && vertexContent != null && vertexContent.length > 3) {
                vertexNum++;
                mVertexFloat.add(Float.parseFloat(vertexContent[2]));
                mVertexFloat.add(Float.parseFloat(vertexContent[3]));
                mVertexFloat.add(Float.parseFloat(vertexContent[4]));
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                vertexContent = line.split(" ");
            }
            numVerts = vertexNum;
            verts = ByteBuffer.allocateDirect(vertexNum * 4);
            verts.order(ByteOrder.nativeOrder());
            for (int i = 0; i < vertexNum; i++) {
                verts.putFloat(mVertexFloat.get(i));
            }
            verts.position(0);
        } catch (Exception e) {
            L.e(TAG, "loadVertex exception: " + e);
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