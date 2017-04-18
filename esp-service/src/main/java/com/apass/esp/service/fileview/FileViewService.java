package com.apass.esp.service.fileview;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.CommonUtils;

@Component
public class FileViewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileViewService.class);

    /**
     * 保存图片的路径
     */
    @Value("${nfs.rootPath}")
    private String              nfsRootPath;

    /**
     * 读取图片的内容
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    public byte[] readFileToByteArray(String filename) throws IOException {
        LOGGER.info("查询图片url:[{}]", filename);
        File f = new File(nfsRootPath + filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            LOGGER.error("图片保存失败", e);
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                LOGGER.error("释放资源失败", e);
            }
            bos.close();
        }
    }

    /**
     * 上传退换货时商品照片
     * 
     * @param paramMap
     * @throws BusinessException 
     */
    public void uploadReturnImage(String requestId, Map<String, Object> paramMap) throws BusinessException {
        String dir = CommonUtils.getValue(paramMap, "dir");
        String imgStr = CommonUtils.getValue(paramMap, "imgFile");
        String imgtype = CommonUtils.getValue(paramMap, "imgType");
        
        // 获取图片的文件名
        String fileName = imgtype + ".jpg";
        FileOutputStream os = null;
        // 输出的文件流保存到本地文件
        File tempFile = new File(nfsRootPath + "/eshop/refund/" + dir);
        //先判断保存文件的目录是否存在
        if (!tempFile.exists()) {
            tempFile.mkdirs();
            LOG.info(requestId, "创建目录", tempFile.toString());
        }

        try {
            String fileStr = tempFile.getPath() + File.separator + fileName;
            File f = new File(fileStr);
            //再判断要保存的文件是否存在
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    LOG.info(requestId, "创建文件", fileStr);
                } catch (IOException e) {
                    LOG.info(requestId, "创建要上传的图片发生异常,文件名称为", fileStr);
                    throw new BusinessException("创建要上传的图片发生异常", e);
                }
            }
            os = new FileOutputStream(f);
            byte[] img = Base64.decodeBase64(imgStr);
            // 开始写入文件
            os.write(img);
        } catch (Exception e) {
            LOG.logstashException(requestId, "向图片文件中写入数据发生异常", "", e);
            throw new BusinessException("向图片文件中写入数据发生异常", e);
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
            } catch (IOException e) {
                LOG.logstashException(requestId, "资源关闭失败", "", e);
            }
        }

    }
}
