package com.sflib.cameralib.compute;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.File;

/**
 * Created by mac on 17/1/14.
 */

public class ImageHashComputation {

    public static String compute(Bitmap sourceImage){

        int width= 8;
        int height = 8;
// targetW，targetH分别表示目标长和宽
//        int type= sourceImage.getType();// 图片类型
        Bitmap thumbImage = null;
        float sx= (float) width / sourceImage.getWidth();
        float sy= (float) height / sourceImage.getHeight();



        // 将图片宽度和高度都设置成一样，以长度短的为准
//        if (b) {
//            if(sx > sy) {
//                sx= sy;
//                width= (int) (sx * sourceImage.getWidth());
//            }else {
//                sy= sx;
//                height= (int) (sy * sourceImage.getHeight());
//            }
//        }


//        thumbImage=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
//        Canvas canvas=new Canvas(thumbImage);
//        canvas.drawBitmap(sourceImage,0,0,new Paint());

        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        thumbImage = Bitmap.createBitmap(sourceImage, 0, 0, sourceImage.getWidth(),
                sourceImage.getHeight(), matrix, true);

// 自定义图片
//        if (type== BufferedImage.TYPE_CUSTOM) { // handmade
//            ColorModelcm = sourceImage.getColorModel();
//            WritableRasterraster = cm.createCompatibleWritableRaster(width,height);
//            booleanalphaPremultiplied = cm.isAlphaPremultiplied();
//            thumbImage= new BufferedImage(cm, raster, alphaPremultiplied, null);
//        } else {
//            // 已知图片，如jpg，png，gif
//            thumbImage= new BufferedImage(width, height, type);
//        }
// 调用画图类画缩小尺寸后的图
//        Graphics2Dg = target.createGraphics();
////smoother than exlax:
//        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g.drawRenderedImage(sourceImage,AffineTransform.getScaleInstance(sx, sy));
//        g.dispose();



        int[]pixels = new int[width * height];
        for (int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                pixels[i* height + j] = rgbToGray(thumbImage.getPixel(i, j));
            }
        }


        int avgPixel= 0;
        int m = 0;
        for (int i =0; i < pixels.length; ++i) {
            m +=pixels[i];
        }
        m = m /pixels.length;
        avgPixel = m;



        int[] comps= new int[width * height];
        for (int i = 0; i < comps.length; i++) {
            if(pixels[i] >= avgPixel) {
                comps[i]= 1;
            }else {
                comps[i]= 0;
            }
        }



        StringBuffer hashCode = new StringBuffer();
        for (int i = 0; i < comps.length; i+= 4) {
            int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2)+ comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 2];
            hashCode.append(binaryToHex(result));//二进制转为16进制
        }
        String sourceHashCode = hashCode.toString();


        return  sourceHashCode;

    }

    public static int getHangmingCode(String sourceHashCode,String hashCode){
        int difference = 0;
        int len =sourceHashCode.length();

        for (int i = 0; i < len; i++) {
            if(sourceHashCode.charAt(i) != hashCode.charAt(i)) {
                difference++;
            }
        }
        return difference;
    }
    /**
     * 二进制转为十六进制
     * @param int binary
     * @return char hex
     */
    private static char binaryToHex(int binary) {
        char ch = ' ';
        switch (binary)
        {
            case 0:
                ch = '0';
                break;
            case 1:
                ch = '1';
                break;
            case 2:
                ch = '2';
                break;
            case 3:
                ch = '3';
                break;
            case 4:
                ch = '4';
                break;
            case 5:
                ch = '5';
                break;
            case 6:
                ch = '6';
                break;
            case 7:
                ch = '7';
                break;
            case 8:
                ch = '8';
                break;
            case 9:
                ch = '9';
                break;
            case 10:
                ch = 'a';
                break;
            case 11:
                ch = 'b';
                break;
            case 12:
                ch = 'c';
                break;
            case 13:
                ch = 'd';
                break;
            case 14:
                ch = 'e';
                break;
            case 15:
                ch = 'f';
                break;
            default:
                ch = ' ';
        }
        return ch;
    }
    /**
     * 灰度值计算
     * @param pixels 彩色RGB值(Red-Green-Blue 红绿蓝)
     * @return int 灰度值
     */
    public static int rgbToGray(int pixels) {
         int _alpha =(pixels >> 24) & 0xFF;
        int _red = (pixels >> 16) & 0xFF;
        int _green = (pixels >> 8) & 0xFF;
        int _blue = (pixels) & 0xFF;
        return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
    }
}
