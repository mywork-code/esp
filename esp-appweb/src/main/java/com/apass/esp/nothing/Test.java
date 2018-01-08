package com.apass.esp.nothing;

import com.apass.esp.domain.entity.FileContent;
import com.apass.esp.domain.entity.FileEntitis;
import com.apass.gfb.framework.utils.GsonUtils;
import com.tencent.tinker.bsdiff.BSDiff;
import com.tencent.tinker.bsdiff.BSPatch;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by xiaohai on 2018/1/8.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String oldFilePath = "D:\\temp\\zip\\1.zip";
        String diffFilePath = "D:\\temp\\zip\\2_1.zip";
        String newFilePath = "D:\\temp\\zip\\demo\\copy2.zip";

        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        File diffFile = new File(diffFilePath);

//        File temp1 = oldFile.length()<newFile.length()?oldFile:newFile;
//        File temp2 = oldFile.length()>newFile.length()?oldFile:newFile;
//        newFile = temp2;
//        oldFile = temp1;
//
//        BSDiff.bsdiff(oldFile,newFile,diffFile);

        BSPatch.patchFast(new FileInputStream(oldFile), new FileInputStream(diffFile), newFile);

//        File file = new File("D:\\data\\nfs\\gfb\\eshop\\bsdiff\\verzip\\wallet\\2\\merge");
//        File pro = new File("D:\\data\\nfs\\gfb\\eshop\\bsdiff\\verzip\\wallet\\2\\2.properties");
//
//        //文件清单
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pro)));
//        StringBuffer sb = new StringBuffer();
//        String str = null;
//        while ((str=br.readLine())!=null){
//            sb.append(str);
//        }
//        List<FileEntitis> list = GsonUtils.convertList(sb.toString(),  new com.google.gson.reflect.TypeToken<List<FileEntitis>>(){});
//
//
//
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
//
//        for(FileEntitis f: list){
//            FileContent fic = f.getFileContent();
//            FileOutputStream bos = new FileOutputStream(new File("D:\\data\\nfs\\gfb\\eshop\\bsdiff\\verzip\\wallet\\2\\chaifen",fic.getName()));
//            byte[] buf = new byte[1024];
//            int len = 0;
//            while((len = bis.read(buf))!=-1){
//                bos.write(buf,0,len);
//            }
//            bos.close();
//        }






    }
}
