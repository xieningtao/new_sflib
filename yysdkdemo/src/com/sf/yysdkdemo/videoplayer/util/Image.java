package com.sf.yysdkdemo.videoplayer.util;

public final class Image {
    public enum ScaleType {
        Fit,
        Overspread,
        Original,
        ClipOverspread
    }

    public static void scaleToW2H2(ScaleType type, int w1, int h1, int w2, int h2, float[] out) {
        float wRatio = 1.0f * w1 / w2;
        float hRatio = 1.0f * h1 / h2;

        switch (type) {
            case Overspread:
                out[0] = w2;
                out[1] = h2;
                break;
            case Original:
                out[0] = w1;
                out[1] = h1;
                break;
            case ClipOverspread:
                if (wRatio > hRatio) {
                    out[0] = 1.0f * w1 * h2 / h1;
                    out[1] = h2;
                } else {
                    out[0] = w2;
                    out[1] = 1.0f * w2 * h1 / w1;
                }
                break;
            case Fit:
            default:
                if (wRatio > hRatio) {
                    out[0] = w2;
                    out[1] = 1.0f * h1 * w2 / w1;
                } else {
                    out[0] = 1.0f * w1 * h2 / h1;
                    out[1] = h2;
                }
                break;
        }
    }
}
