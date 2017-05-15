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
