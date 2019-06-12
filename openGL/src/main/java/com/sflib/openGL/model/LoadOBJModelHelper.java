package com.sflib.openGL.model;

import android.content.res.AssetManager;


import com.sf.loglib.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetEase on 2016/11/8 0008.
 */

public class LoadOBJModelHelper {

    public enum BUFFER_TYPE {
        BUFFER_TYPE_VERTEX, BUFFER_TYPE_TEXTURE_COORD, BUFFER_TYPE_NORMALS, BUFFER_TYPE_INDICES
    }

    private final String TAG = LoadOBJModelHelper.class.getName();
    private ByteBuffer verts;
    private ByteBuffer textCoords;
    private ByteBuffer norms;
    private ByteBuffer mIndBuff;
    private int numVerts = 0;
    private List<Float> mVertexFloat = new ArrayList<>();
    private List<Integer> mIndexInteger = new ArrayList<>();

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
                fragmentNum++;
                mIndexInteger.add(Integer.parseInt(fragmentIndex[1]));
                mIndexInteger.add(Integer.parseInt(fragmentIndex[2]));
                mIndexInteger.add(Integer.parseInt(fragmentIndex[3]));
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                fragmentIndex = line.split(" ");
            }

            mIndBuff = ByteBuffer.allocateDirect(fragmentNum * 4);
            mIndBuff.order(ByteOrder.nativeOrder());
            for (int i = 0; i < fragmentNum; i++) {
                mIndBuff.putInt(mIndexInteger.get(i));
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
                    L.info(TAG, "loadVertex file end");
                    return;
                }
            }

            String vertexContent[] = line.split(" ");
            while (line.startsWith("v") && vertexContent != null && vertexContent.length > 3) {
                vertexNum++;
                mVertexFloat.add(Float.parseFloat(vertexContent[1]));
                mVertexFloat.add(Float.parseFloat(vertexContent[2]));
                mVertexFloat.add(Float.parseFloat(vertexContent[3]));
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                vertexContent = line.split(" ");
            }

            verts = ByteBuffer.allocateDirect(vertexNum * 4);
            verts.order(ByteOrder.nativeOrder());
            for (int i = 0; i < vertexNum; i++) {
                verts.putFloat(mVertexFloat.get(i));
            }
            verts.position(0);
        } catch (Exception e) {
            L.error(TAG, "loadVertex exception: " + e);
        }

    }


    public void loadModel(AssetManager assetManager, String filename)
            throws IOException {
        InputStream is = null;
        try {
            is = assetManager.open(filename);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));

            String line = reader.readLine();
            int floatsToRead = Integer.parseInt(line);
            numVerts = floatsToRead / 3;

            verts = ByteBuffer.allocateDirect(floatsToRead * 4);
            verts.order(ByteOrder.nativeOrder());
            for (int i = 0; i < floatsToRead; i++) {
                verts.putFloat(Float.parseFloat(reader.readLine()));
            }
            verts.rewind();

            line = reader.readLine();
            floatsToRead = Integer.parseInt(line);

            norms = ByteBuffer.allocateDirect(floatsToRead * 4);
            norms.order(ByteOrder.nativeOrder());
            for (int i = 0; i < floatsToRead; i++) {
                norms.putFloat(Float.parseFloat(reader.readLine()));
            }
            norms.rewind();

            line = reader.readLine();
            floatsToRead = Integer.parseInt(line);

            textCoords = ByteBuffer.allocateDirect(floatsToRead * 4);
            textCoords.order(ByteOrder.nativeOrder());
            for (int i = 0; i < floatsToRead; i++) {
                textCoords.putFloat(Float.parseFloat(reader.readLine()));
            }
            textCoords.rewind();

        } finally {
            if (is != null)
                is.close();
        }
    }


    public Buffer getBuffer(BUFFER_TYPE bufferType) {
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
        return 0;
    }
}
