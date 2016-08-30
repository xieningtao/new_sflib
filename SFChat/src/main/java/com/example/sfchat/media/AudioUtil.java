package com.example.sfchat.media;

import android.os.Build;

public class AudioUtil {

    /*
     * Converts a byte to a short, in LITTLE_ENDIAN format
     */
    public static short getShort(byte argB1, byte argB2) {
        return (short) (argB1 | (argB2 << 8));
    }

    public static int getAacBitRate(int sampleRates, int channel) {
        int scale = 441;
        if (sampleRates % 8000 == 0)
            scale = 480;
        int bitRate = 640 * channel * sampleRates / scale;
        return bitRate;
    }

    public static boolean canNoiseSuppressor() {
        boolean ret = true;
        String info = Build.CPU_ABI;
        if (info.startsWith("armeabi")) {
            if (info.contains("armeabi-v6") || info.equalsIgnoreCase("armeabi"))
                ret = false;
        } else
            ret = false;
        return ret;
    }
}
