package com;

import com.tencent.tinker.bsdiff.BSDiff;
import com.tencent.tinker.bsdiff.BSPatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by xiaohai on 2017/11/16.
 */
public class TestClass {
    public static void main(String[] args) throws IOException {
        File oldFile = new File("D:\\data\\nfs\\gfb\\eshop\\bsdiff\\verzip\\1.zip");
//        File newFile = new File("D:\\temp\\2.zip");
        File diffFile = new File("D:\\data\\nfs\\gfb\\eshop\\bsdiff\\patchzip\\2_1.zip");

//        BSDiff.bsdiff(oldFile,newFile,diffFile);
        BSPatch.patchFast(new FileInputStream(oldFile),new FileInputStream(diffFile),new File("D:\\data\\nfs\\gfb\\eshop\\bsdiff\\patchzip\\hebing.zip"));

    }
}
