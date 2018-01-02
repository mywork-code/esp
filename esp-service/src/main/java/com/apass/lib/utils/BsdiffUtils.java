package com.apass.lib.utils;

/**
 * Created by fuchen on 2017/11/14.
 */

public class BsdiffUtils {

    private static final BsdiffUtils INSTANCE = new BsdiffUtils();

    static {
        System.loadLibrary("bsdiff");
    }

    private BsdiffUtils(){}


    public static BsdiffUtils getInstance(){
        return INSTANCE;
    }

    //生成差分包
    public native int bsdiff(String oldpath, String newpath, String patchpath);

    //合并差分包
    public native int bspatch(String oldpath, String newpath, String patchpath);

}
