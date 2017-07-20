package com.apass.esp.service.common;

import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.EncodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by xianzhi.wang on 2017/5/11.
 */
@Service
public class ImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Value("${esp.image.uri}")
    private String espImageUri;

    /**
     * @param imageUrl 加密前的图片路径
     * @return String
     */
    public String getImageUrl(String imageUrl) throws BusinessException {
        if (StringUtils.isBlank(espImageUri)) {
            throw new BusinessException("espImageUri为空");
        }
        if (StringUtils.isBlank(imageUrl)) {
            return null;
        } else {
            return espImageUri + "/static" + imageUrl;
        }
    }
    /**
     * @param imageUrl 加密前的京东图片路径
     * 京东图片需要在前面添加http://img13.360buyimg.com/n0/
     * 其中n0(最大图)、n1(350*350px)、n2(160*160px)、n3(130*130px)、n4(100*100px) 为图片大小
     * @return String
     */
    public String getJDImageUrl(String imageUrl,String n) throws BusinessException {
        if (StringUtils.isBlank(espImageUri)) {
            throw new BusinessException("espImageUri为空");
        }
        if (StringUtils.isBlank(imageUrl)) {
            return null;
        } else {
            return "http://img13.360buyimg.com/" +n+ "/" + imageUrl;
        }
    }
    /**
     * 加密后的
     * @param imageUrl
     * @return
     * @throws BusinessException
     */
    public String getDecImageUrl(String imageUrl) throws BusinessException {
        if (StringUtils.isBlank(espImageUri)) {
            throw new BusinessException("espImageUri为空");
        }
        if (StringUtils.isBlank(imageUrl)) {
            return null;
        } else {
            return espImageUri + "/static" + EncodeUtils.base64Encode(imageUrl);
        }
    }
}
