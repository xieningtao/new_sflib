package com.sf.SFSample.ui;

import com.sf.loglib.L;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

public class ShellCmd {
    public static final String TAG = "ShellCmd";

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            L.error(TAG, "Unable to read sysprop " + propName + " exception: " + ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    L.error(TAG, "Exception while closing InputStream" + e);
                }
            }
        }
        return line;
    }

    public static ArrayList<String> doShcmd(String cmd) {
        String line;
        ArrayList<String> out = new ArrayList<String>();
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("sh");
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            //将命令写入
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            while ((line = input.readLine()) != null) {
                out.add(line);
            }
        } catch (IOException ex) {
            L.error(TAG, "Unable to do cmd: " + cmd + " exception: " + ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    L.error(TAG, "Exception while closing InputStream exception:" + e);
                }
            }
        }
        return out;
    }
    /**
     * 使用adb命令时，不用传入adb shell
     * @param cmd
     * @return
     *
     *
     * 1、cat /proc/version
     */
       public static ArrayList<String> doSucmd(String cmd) {
        String line;
        ArrayList<String> out = new ArrayList<String>();
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            //将命令写入
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            while ((line = input.readLine()) != null) {
                out.add(line);
            }
        } catch (IOException ex) {
            L.error(TAG, "Unable to do cmd: " + cmd + " exception: " + ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    L.error(TAG, "Exception while closing InputStream exception:" + e);
                }
            }
        }
        return out;
    }
}
