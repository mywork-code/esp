package com.apass.esp.service.contract;

import com.apass.esp.domain.dto.contract.SealRequest;
import com.apass.esp.domain.dto.contract.SealResponse;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.logstash.LOG;
import com.apass.gfb.framework.utils.EncodeUtils;
import com.apass.gfb.framework.utils.GsonUtils;
import com.apass.gfb.framework.utils.HttpClientUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 合同签章
 */
@Component
public class ContractSealClient {
    /**
     * 签章地址
     */
    @Value("${contract.signature.seal.url}")
    private String sealAddress; // = "http://10.138.60.115:8089/Api/CompoundSeal";
    /**
     * PDF合同保存目录
     */
    @Value("${contract.signature.save.path}")
    private String contractSavePath; // = "E:/";
    /**
     * PDF字体存放目录
     */
    @Value("${contract.pdf.fonts.path}")
    private String pdfFontsSavePath; // = "E:/";

    /**
     * 合同签章
     *
     * @param request - 签章请求参数
     * @return SealResponse
     */
    public SealResponse handleSeal(String mainOrderId, SealRequest request) throws BusinessException {
        String requestId = "contract_ps_" + mainOrderId;
        String methodDesc = "商品购销及服务合同签章";
        try {
            String fileName = request.getFileName();
            String requestJson = GsonUtils.toJson(request);
            LOG.logstashRequest(requestId, methodDesc, requestJson);
            StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            String respJson = HttpClientUtils.getMethodPostResponse(sealAddress, entity);
            LOG.logstashResponse(requestId, methodDesc, respJson);
            SealResponse tempResp = GsonUtils.convertObj(respJson, SealResponse.class);
            if (tempResp == null || !tempResp.isSuccess()) {
                throw new BusinessException(methodDesc + "失败");
            }
            return tempResp;
        } catch (Exception e) {
            throw new BusinessException(methodDesc + "异常", e);
        }
    }

    /**
     * 获取合同保存目录
     *
     * @param userId - 用户ID
     * @return String
     */
    public String getContractSavePath(Long userId) {
        String path = contractSavePath + File.separator + userId + File.separator;
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return path;
    }

    /**
     * PDF中文字体
     *
     * @return
     */
    public String getPdfFontsPath() {
        return pdfFontsSavePath;
    }

    /**
     * 合同签章并保存到磁盘
     *
     * @param userId      - 客户ID
     * @param mainOrderId - 订单ID
     * @param request     - 签章Request
     * @return
     * @throws BusinessException
     */
    public SealResponse handleSealAndSave(Long userId, String mainOrderId, SealRequest request) throws BusinessException {
        try {
            SealResponse tempResp = handleSeal(mainOrderId, request);
            String fileName = request.getFileName();
            String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_sig.pdf";
            String fullPath = getContractSavePath(userId) + newFileName;
            FileUtils.writeByteArrayToFile(new File(fullPath), EncodeUtils.decodeBase64(tempResp.getFileBufferString()));
            return tempResp;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("保存签章合同失败", e);
        }
    }
}
