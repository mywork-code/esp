package com.apass.esp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.apass.gfb.framework.utils.ImageUtils;

/**
 * 文件处理工具
 * 
 * @author zhanwendong
 *
 */
public class FileUtilsCommons {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtilsCommons.class);

    private FileUtilsCommons() {

    }

    /**
     * 公用上传文件方法
     * 
     * @param fatherPath
     * @param url文件绝对路径
     * @param file
     *            文件
     */
    public static void uploadFilesUtil(String fatherPath, String urlOld, MultipartFile file) {
        InputStream in = null;
        FileOutputStream out = null;

        try {
            String url = fatherPath + urlOld;
            // 判断目录是否存在
            String fil = new File(url).getParent();
            if (!new File(fil).isDirectory()) {
                new File(fil).mkdirs();
            }
            in = file.getInputStream();
            out = new FileOutputStream(url);

            int n = 0;// 每次读取的字节长度
            byte[] bb = new byte[1024];// 存储每次读取的内容
            while ((n = in.read(bb)) != -1) {
                out.write(bb, 0, n);// 将读取的内容，写入到输出流当中
            }

            //			boolean b = NarrowImageUtils.saveAndNarrowImageByIs(in, url);//in:输入流，url图片地址
            //			if(!b){
            //			    throw new RuntimeException("文件上传失败");
            //			}
        } catch (Exception e) {
            LOGGER.error("文件上传失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("关闭输入输出流异常", e);
                } // 关闭输入输出流
            }
            if (out != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("关闭输入输出流异常", e);
                }
            }
        }
    }
    /**
     * 公用上传文件方法
     * 
     * @param fatherPath
     * @param url文件绝对路径
     * @param bytefile文件
     */
    public static void uploadByteFilesUtil(String fatherPath, String urlOld, byte[] file) {
        FileOutputStream out = null;
        try {
            String url = fatherPath + urlOld;
            // 判断目录是否存在
            String fil = new File(url).getParent();
            if (!new File(fil).isDirectory()) {
                new File(fil).mkdirs();
            }
            out = new FileOutputStream(url);
            out.write(file);
        } catch (Exception e) {
            LOGGER.error("文件上传失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("关闭输入输出流异常", e);
                } // 关闭输入输出流
            }
        }
    }
    /**
     * 获取base64文件
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static String loadPicBase64String(String fatherPath, String urlOld) throws Exception {
        if (!StringUtils.isBlank(urlOld)) {
            String url = fatherPath + urlOld;
            String colorBigSquare = ImageUtils.imageToBase64(url);
            if (colorBigSquare.length() > 0) {
                return colorBigSquare;
            }
        }
        return "";
    }

    /**
     * 获取Byte文件
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static byte[] loadPicByte(String fatherPath, String urlOld) throws Exception {
        if (!StringUtils.isBlank(urlOld)) {
            String url = fatherPath + urlOld;
            File imgFile = new File(url);
            byte[] xlsbyte = FileUtils.readFileToByteArray(imgFile);
            return xlsbyte;
        }
        return null;
    }
}