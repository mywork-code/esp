package com.apass.esp.nothing;

import com.tencent.tinker.bsdiff.BSDiff;

import java.io.File;
import java.io.IOException;

/**
 * Created by xiaohai on 2018/1/8.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String oldFilePath = "D:\\temp\\zip\\demo\\6.zip";
        String newFilePath = "D:\\temp\\zip\\demo\\7.zip";
        String diffFilePath = "D:\\temp\\zip\\demo\\7_6";;
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        File diffFile = new File(diffFilePath);

        File temp1 = oldFile.length()<newFile.length()?oldFile:newFile;
        File temp2 = oldFile.length()>newFile.length()?oldFile:newFile;
        newFile = temp2;
        oldFile = temp1;

        BSDiff.bsdiff(oldFile,newFile,diffFile);
    }
}
