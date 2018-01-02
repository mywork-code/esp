package com.apass.esp.utils;

import com.apass.esp.domain.entity.FileContent;
import com.apass.esp.domain.entity.FileEntitis;
import com.apass.gfb.framework.utils.GsonUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.jexl2.UnifiedJEXL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by xiaohai on 2017/12/28.
 */
@SuppressWarnings("unchecked")
public class ZipUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);
    private static final int SIZE = 1048576;

    private String zipPath;
    private String zipName;

    /**
     * 压缩文件-由于out要在递归调用外,所以封装一个方法用来
     * 调用ZipFiles(ZipOutputStream out,String path,File... srcFiles)
     * @param zip
     * @param path
     * @param srcFiles
     * @throws IOException
     * @author isea533
     */
    public static void ZipFiles(File zip,String path,File... srcFiles) throws IOException{
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
        ZipUtil.ZipFiles(out,path,srcFiles);
        out.close();
        LOGGER.info("*****************压缩完毕*******************");
    }
    /**
     * 压缩文件-File
     * @param out  zip文件输出流
     * @param srcFiles 被压缩源文件
     * @author isea533
     */
    public static void ZipFiles(ZipOutputStream out, String path, File... srcFiles){
//        path = path.replaceAll("\\*", "/");
//        if(!path.endsWith("/")){
//            path+="/";
//        }
        byte[] buf = new byte[1024];
        try {
            for(int i=0;i<srcFiles.length;i++){
                if(srcFiles[i].isDirectory()){
                    File[] files = srcFiles[i].listFiles();
                    String srcPath = srcFiles[i].getName();
                    srcPath = srcPath.replaceAll("\\*", "/");
                    if(!srcPath.endsWith("/")){
                        srcPath+="/";
                    }
                    out.putNextEntry(new ZipEntry(path+srcPath));
                    ZipFiles(out,path+srcPath,files);
                }
                else{
                    FileInputStream in = new FileInputStream(srcFiles[i]);
                    System.out.println(path + srcFiles[i].getName());
                    out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
                    int len;
                    while((len=in.read(buf))>0){
                        out.write(buf,0,len);
                    }
                    out.closeEntry();
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 解压到指定目录
     * @param zipPath
     * @param descDir
     * @author isea533
     */
    public void unZipFiles(String zipPath,String descDir)throws IOException{
        unZipFiles(new File(zipPath), descDir);
    }
    /**
     * 解压文件到指定目录,并生成文件清单存储在文件里,且生成合成文件
     * @param zipFile
     * @param descDir
     * @author isea533
     */
    @SuppressWarnings("rawtypes")//禁止不使用泛型警告
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public void unZipFiles(File zipFile,String descDir)throws IOException{
        List<InputStream> ins = Lists.newArrayList();
        //合并文件流
        SequenceInputStream sis = null;
        BufferedOutputStream bos = null;
        //写配置文件流
        BufferedWriter bw = null;

        try{
            List<FileEntitis> list = Lists.newArrayList();//存储文件清单

            int countStart = 0;//记录偏移量起始位
            int countEnd = 0;//记录偏移量最后位
            File f = new File(descDir,zipFile.getName().split("\\.")[0]+".properties");//记录文件清单列表

            File pathFile = new File(descDir);
            if(!pathFile.exists()){
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile);
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                FileEntitis fileEntitis = new FileEntitis();
                FileContent fileContent = new FileContent();
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                fileEntitis.setId(zipEntryName);
                fileContent.setName(zipEntryName);
                InputStream in = zip.getInputStream(entry);
                countEnd=countStart+in.available();
                fileContent.setExcursionSize(String.valueOf(countStart)+","+String.valueOf(countEnd));
                //TODO 变更环境
                fileContent.setUrl("http://espapp.sit.apass.cn/static/"+zipPath+f.getName());
                fileEntitis.setFileContent(fileContent);
                list.add(fileEntitis);
                countStart = countEnd+1;
                String outPath = (descDir+zipEntryName).replaceAll("\\\\", "/");;
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()){
                    continue;
                }
                //输出文件路径信息
                LOGGER.info("路径："+outPath+",大小:"+in.available());

                OutputStream out = new FileOutputStream(outPath);
                //每个文件对应一个读取流，用来合并文件
                InputStream mergein = new FileInputStream(outPath);
                ins.add(mergein);
                byte[] buf1 = new byte[1024];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }

                out.close();
                in.close();
            }

            //生成合并文件
            Enumeration en = Collections.enumeration(ins);
            sis = new SequenceInputStream(en);
            bos = new BufferedOutputStream(new FileOutputStream(new File(descDir,"merge")));
            byte[] buf = new byte[SIZE];
            int len = 0;
            while((len = sis.read(buf))!=-1){
                bos.write(buf,0,len);
                bos.flush();
            }

            //list转成数组存储到.properties文件中
            String json = GsonUtils.toJson(list);
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            bw.write(json);
            bw.flush();
        }finally {
            if(bw!=null){
                bw.close();
            }
            if(bos!=null){
                bos.close();
            }
            if(sis!=null){
                sis.close();
            }

            if(CollectionUtils.isNotEmpty(ins)){
                for(InputStream in : ins){
                    in.close();
                }
            }
        }

        LOGGER.info("******************解压完毕********************");
    }

    public void unZipFiles(String rootPath, String zipPath, String zipName) throws IOException {
        this.zipPath = zipPath;
        this.zipName = zipName;
        unZipFiles(rootPath+zipPath+zipName,rootPath+zipPath);
    }


}
