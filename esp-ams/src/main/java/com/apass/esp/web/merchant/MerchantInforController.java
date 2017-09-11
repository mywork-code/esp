package com.apass.esp.web.merchant;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apass.esp.domain.Response;
import com.apass.esp.domain.entity.merchant.MerchantInfoEntity;
import com.apass.esp.domain.enums.MerchantStatus;
import com.apass.esp.domain.enums.StatusCode;
import com.apass.esp.service.merchant.MerchantInforService;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.log.LogAnnotion;
import com.apass.gfb.framework.log.LogValueTypeEnum;
import com.apass.gfb.framework.mybatis.page.Page;
import com.apass.gfb.framework.mybatis.page.Pagination;
import com.apass.gfb.framework.security.controller.BaseController;
import com.apass.gfb.framework.security.toolkit.SpringSecurityUtils;
import com.apass.gfb.framework.security.userdetails.ListeningCustomSecurityUserDetails;
import com.apass.gfb.framework.utils.HttpWebUtils;

@Controller
@RequestMapping(value = "/application/merchantinfor/merchant")
public class MerchantInforController extends BaseController {
    /**
     * 日志
     */
    private static final Logger  LOGGER                           = LoggerFactory
        .getLogger(MerchantInforController.class);
    private static final String  CREDIT_MERCHANTINFOR_URL         = "merchant/merchantInforList";
    private static final String  CREDIT_MERCHANTINFORPRESERVE_URL = "merchant/merchantInforpreserve";
    private static final String  CREDIT_MERCHANTINFORCHECK_URL    = "merchant/merchantInforcheck";

    @Autowired
    private MerchantInforService merchantInforService;

    /**
     * 商户信息初始化
     */
    @RequestMapping("/page")
    public ModelAndView merchantInforPage(Map<String, Object> paramMap) {
    	if(SpringSecurityUtils.hasPermission("MERCHANT_MANAGE_EDIT")) {
    		paramMap.put("grantedAuthority", "permission");
		}
        return new ModelAndView(CREDIT_MERCHANTINFOR_URL,paramMap);
    }

    @RequestMapping("/pagepreserve")
    public String merchantPagepreserve(Map<String, Object> paramMap) {
        return CREDIT_MERCHANTINFORPRESERVE_URL;
    }

    @RequestMapping("/checkPage")
    public ModelAndView merchantPageCheck(Map<String, Object> paramMap) {
    	if(SpringSecurityUtils.hasPermission("MERCHANT_CHECK_BATCH")) {
    		paramMap.put("grantedAuthority", "permission");
		}
        return new ModelAndView(CREDIT_MERCHANTINFORCHECK_URL,paramMap);
    }

    /**
     * 商户信息查询(分页)
     */
    @ResponseBody
    @RequestMapping("/query")
    public ResponsePageBody<MerchantInfoEntity> queryMerchantInforPage(HttpServletRequest request) {
        ResponsePageBody<MerchantInfoEntity> respBody = new ResponsePageBody<MerchantInfoEntity>();
        try {
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            Integer pageNoNum = Integer.valueOf(pageNo);
            Integer pageSizeNum = Integer.valueOf(pageSize);
            Page page = new Page();
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);

            String merchantName = HttpWebUtils.getValue(request, "merchantName");
            String merchantType = HttpWebUtils.getValue(request, "merchantType");

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("merchantName", merchantName);
            map.put("merchantType", merchantType);

            Pagination<MerchantInfoEntity> resultPage = merchantInforService.queryMerchantInforPage(map, page);
            if (resultPage == null) {
                respBody.setTotal(0);
                // respBody.setStatus("1");
                respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
                return respBody;
            }
            respBody.setTotal(resultPage.getTotalCount());
            respBody.setRows(resultPage.getDataList());
            // respBody.setStatus("1");
            respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
        } catch (Exception e) {
            LOGGER.error("商户信息查询失败", e);
            respBody.setStatus("0");
            respBody.setMsg("商户信息查询失败");
        }
        return respBody;
    }

    /**
     * 待审核商户信息查询(分页)
     */
    @ResponseBody
    @RequestMapping("/queryCheck")
    public ResponsePageBody<MerchantInfoEntity> queryCheckMerchantPage(HttpServletRequest request) {
        ResponsePageBody<MerchantInfoEntity> respBody = new ResponsePageBody<MerchantInfoEntity>();
        try {
            String pageNo = HttpWebUtils.getValue(request, "page");
            String pageSize = HttpWebUtils.getValue(request, "rows");
            Integer pageNoNum = Integer.valueOf(pageNo);
            Integer pageSizeNum = Integer.valueOf(pageSize);
            Page page = new Page();
            page.setPage(pageNoNum <= 0 ? 1 : pageNoNum);
            page.setLimit(pageSizeNum <= 0 ? 1 : pageSizeNum);

            String merchantName = HttpWebUtils.getValue(request, "merchantName");
            String merchantType = HttpWebUtils.getValue(request, "merchantType");

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("merchantName", merchantName);
            map.put("merchantType", merchantType);
            map.put("status", MerchantStatus.MERCHANT_CHECK.getCode());

            Pagination<MerchantInfoEntity> resultPage = merchantInforService.queryMerchantCheckPage(map, page);
            if (resultPage == null) {
                respBody.setTotal(0);
                // respBody.setStatus("1");
                respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
                return respBody;
            }
            respBody.setTotal(resultPage.getTotalCount());
            respBody.setRows(resultPage.getDataList());
            // respBody.setStatus("1");
            respBody.setStatus(StatusCode.SUCCESS_CODE.getCode());
        } catch (Exception e) {
            LOGGER.error("商户信息查询失败", e);
            respBody.setStatus("0");
            respBody.setMsg("商户信息查询失败");
        }
        return respBody;
    }

    /**
     * 商户信息查询(临时表)
     */
    @ResponseBody
    @RequestMapping("/queryByMerchantcode")
    public Response queryByMerchantCode() {
        MerchantInfoEntity merchantInfoEntity = null;
        try {
            ListeningCustomSecurityUserDetails listeningCustomSecurityUserDetails = SpringSecurityUtils
                .getLoginUserDetails();
            String merchantCode = listeningCustomSecurityUserDetails.getMerchantCode();
            if (StringUtils.isBlank(merchantCode)) {
                return Response.fail("您没有关联商户,请先关联商户，再进行该操作！");
            }
            merchantInfoEntity = merchantInforService.queryTempMByMerchantCode(merchantCode);
        } catch (Exception e) {
            LOGGER.error("商户信息查询失败", e);
        }

        return Response.success("success", merchantInfoEntity);
    }

    /**
     * 添加商户信息
     */
    @ResponseBody
    @RequestMapping("/add")
    @LogAnnotion(operationType = "添加商户信息", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response addMerchantInfor(HttpServletRequest request) {
        try {
            MerchantInfoEntity mity = this.encapMethod(request);
            // 根据商户编码查询商户信息
            MerchantInfoEntity merchantInfoEntity = merchantInforService.queryByMerchantCode(mity.getMerchantCode());
            if (merchantInfoEntity != null) {
                return Response.fail("商户已存在，请不要重复添加！");
            }
            mity.setStatus(MerchantStatus.MERCHANT_VALID.getCode());// 正常
            String name = SpringSecurityUtils.getLoginUserDetails().getRealName();// 获取创建人
            if (null != name && !name.trim().isEmpty()) {
                mity.setCreateUser(name);
                mity.setUpdateUser(name);
            }
            // 调用service层,添加商户信息
            int result = merchantInforService.addMerchantInfor(mity);

            // 如果添加成功，返回1，否则添加失败
            if (result == 1) {
                return Response.success("添加商户信息成功！");
            } else {
                return Response.fail("添加商户信息失败！");
            }
        } catch (Exception e) {
            LOGGER.error("添加商户信息失败了", e);
            return Response.fail("添加商户信息失败!");
        }
    }

    /**
     * 编辑商户信息（主表）
     */
    @ResponseBody
    @RequestMapping("/edit")
    @LogAnnotion(operationType = "编辑(主)商户信息", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response editMerchantInfor(HttpServletRequest request) {
        try {
            MerchantInfoEntity mity = this.encapMethod(request);
            if (StringUtils.isBlank(mity.getStatus())) {
                mity.setStatus(MerchantStatus.MERCHANT_VALID.getCode());
            }
            mity.setRemark(HttpWebUtils.getValue(request, "remark"));
            // 调用service层编辑商户户信息
            int result = merchantInforService.editMerchantInfor(mity);
            if (result == 1) {
                return Response.success("修改商户信息成功！");
            } else {
                return Response.fail("修改商户信息失败！");
            }
        } catch (ParseException e) {
            LOGGER.error("日期格式解析失败", e);
            return Response.fail("日期格式解析失败,修改商户信息失败！");
        } catch (BusinessException e) {
            LOGGER.error("修改商户信息失败", e);
            return Response.fail("修改商户信息失败！");
        }
    }

    /**
     * 编辑商户信息(临时表)
     */
    @ResponseBody
    @RequestMapping("/editTemp")
    @LogAnnotion(operationType = "编辑(临)商户信息", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response editMerchantInforTemp(HttpServletRequest request) {
        try {
            MerchantInfoEntity mity = this.encapMethod(request);
            // 如果是保存，设置商户状态为1：正常
            if (StringUtils.isBlank(mity.getStatus())) {
                mity.setStatus(MerchantStatus.MERCHANT_VALID.getCode());
            }
            mity.setRemark(HttpWebUtils.getValue(request, "remark"));
            // 调用service层编辑商户户信息
            int result = merchantInforService.editMerchantInforTemp(mity);
            if (result == 1) {
                return Response.success("修改商户信息成功！");
            } else {
                return Response.fail("修改商户信息失败！");
            }
        } catch (ParseException e) {
            LOGGER.error("日期格式解析失败", e);
            return Response.fail("日期格式解析失败,修改商户信息失败！");
        } catch (BusinessException e) {
            LOGGER.error("修改商户信息失败", e);
            return Response.fail("修改商户信息失败！");
        }

    }

    /**
     * 编辑商户状态
     */
    @ResponseBody
    @RequestMapping("/editStatus")
    @LogAnnotion(operationType = "编辑商户状态", valueType = LogValueTypeEnum.VALUE_REQUEST)
    public Response editMerchantStatus(HttpServletRequest request) {
        try {
            String merchantCode = HttpWebUtils.getValue(request, "merchantCode");
            String status = HttpWebUtils.getValue(request, "status");

            MerchantInfoEntity mity = new MerchantInfoEntity();

            if (null != merchantCode && !merchantCode.trim().isEmpty()) {
                mity.setMerchantCode(merchantCode);
            }
            if (null != status && !status.trim().isEmpty()) {
                mity.setStatus(status);
            }

            int result = merchantInforService.editMerchantStatus(mity);
            if (result == 1) {
                return Response.success("修改商户信息成功！");
            } else {
                return Response.fail("商户状态修改失败！");
            }
        } catch (Exception e) {
            LOGGER.error("商户状态修改失败", e);
            return Response.fail("商户状态修改失败！");
        }
    }

    /**
     * check审核
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkview", method = RequestMethod.POST)
    public Response checkview(HttpServletRequest request) {
        String merchantCodes = HttpWebUtils.getValue(request, "merchantCodes");
        String flag = HttpWebUtils.getValue(request, "flag");
        String message = HttpWebUtils.getValue(request, "message");
        Integer result = 0;

        if (!StringUtils.isBlank(merchantCodes)) {
            int end = merchantCodes.length() - 1;
            merchantCodes = merchantCodes.substring(1, end);
            String[] strArr = merchantCodes.split(",");
            if (null != strArr && strArr.length >= 0) {
                for (int i = 0; i < strArr.length; i++) {
                    MerchantInfoEntity mity = new MerchantInfoEntity();
                    String merchantCode = strArr[i].substring(1, strArr[i].length() - 1);
                    mity.setMerchantCode(merchantCode);
                    mity.setStatus(MerchantStatus.MERCHANT_VALID.getCode());
                    mity.setUpdateUser(SpringSecurityUtils.getLoginUserDetails().getUsername());
                    mity.setRemark(message);
                    if (flag.equals("pass")) {
                        result = merchantInforService.updateMerchantService(mity);
                    } else {
                        result = merchantInforService.updateMerchantTempService(mity);
                    }
                }
            }
        }

        if (flag.equals("pass")) {
            if (result == 1) {
                return Response.success("审核通过成功！");
            } else {
                return Response.fail("服务器忙，请稍后重试！");
            }
        } else {
            if (result == 1) {
                return Response.success("审核驳回成功！");
            } else {
                return Response.fail("服务器忙，请稍后重试！");
            }
        }
    }

    /**
     * 封装参数到对象
     * 
     * @throws ParseException
     */
    private MerchantInfoEntity encapMethod(HttpServletRequest request) throws ParseException {
        String id = HttpWebUtils.getValue(request, "id");
        String merchantCode = HttpWebUtils.getValue(request, "merchantCode");
        String merchantName = HttpWebUtils.getValue(request, "merchantName");
        String channel = HttpWebUtils.getValue(request, "channel");
        String merchantProvince = HttpWebUtils.getValue(request, "merchantProvince");
        String merchantCity = HttpWebUtils.getValue(request, "merchantCity");
        String merchantArea = HttpWebUtils.getValue(request, "merchantArea");
        String merchantAddress = HttpWebUtils.getValue(request, "merchantAddress");
        String merchantReturnAddress = HttpWebUtils.getValue(request, "merchantReturnAddress");
        String merchantReturnName = HttpWebUtils.getValue(request, "merchantReturnName");
        String merchantReturnPhone = HttpWebUtils.getValue(request, "merchantReturnPhone");
        String merchantReturnPostCode = HttpWebUtils.getValue(request, "merchantReturnPostCode");
        String merchantPostcode = HttpWebUtils.getValue(request, "merchantPostcode");
        String merchantType = HttpWebUtils.getValue(request, "merchantType");
        String merchantNickname = HttpWebUtils.getValue(request, "merchantNickname");
        String isContainFreight = HttpWebUtils.getValue(request, "isContainFreight");
        String merchantSettlementDate = HttpWebUtils.getValue(request, "merchantSettlementDate");
        String settlementBankName = HttpWebUtils.getValue(request, "settlementBankName");
        String settlementCardNo = HttpWebUtils.getValue(request, "settlementCardNo");
        String manageType = HttpWebUtils.getValue(request, "manageType");
        String orgCode = HttpWebUtils.getValue(request, "orgCode");
        String status = HttpWebUtils.getValue(request, "status");

        MerchantInfoEntity mity = new MerchantInfoEntity();
        // 封装参数到对象中
        if (null != id && !id.trim().isEmpty()) {
            mity.setId(Long.parseLong(id));
        }
        if (null != merchantCode && !merchantCode.trim().isEmpty()) {
            mity.setMerchantCode(merchantCode);
        }
        if (null != merchantName && !merchantName.trim().isEmpty()) {
            mity.setMerchantName(merchantName);
        }
        if (null != channel && !channel.trim().isEmpty()) {
            mity.setChannel(Integer.valueOf(channel));
        }
        if (null != merchantProvince && !merchantProvince.trim().isEmpty()) {
            mity.setMerchantProvince(merchantProvince);
        }
        if (null != merchantCity && !merchantCity.trim().isEmpty()) {
            mity.setMerchantCity(merchantCity);
        }
        if (null != merchantArea && !merchantArea.trim().isEmpty()) {
            mity.setMerchantArea(merchantArea);
        }
        if (null != merchantAddress && !merchantAddress.trim().isEmpty()) {
            mity.setMerchantAddress(merchantAddress);
        }
        if (null != merchantReturnAddress && !merchantReturnAddress.trim().isEmpty()) {
            mity.setMerchantReturnAddress(merchantReturnAddress);
        }
        if (null != merchantReturnName && !merchantReturnName.trim().isEmpty()) {
            mity.setMerchantReturnName(merchantReturnName);
        }  
        if (null != merchantReturnPhone && !merchantReturnPhone.trim().isEmpty()) {
            mity.setMerchantReturnPhone(merchantReturnPhone);;
        } 
        if (null != merchantReturnPostCode && !merchantReturnPostCode.trim().isEmpty()) {
            mity.setMerchantReturnPostCode(merchantReturnPostCode);
        }
        if (null != merchantPostcode && !merchantPostcode.trim().isEmpty()) {
            mity.setMerchantPostcode(merchantPostcode);
        }
        if (null != merchantType && !merchantType.trim().isEmpty()) {
            mity.setMerchantType(merchantType);
        }

        if (null != merchantNickname && !merchantNickname.trim().isEmpty()) {
            mity.setMerchantNickname(merchantNickname);
        }
        if (null != isContainFreight && !isContainFreight.trim().isEmpty()) {
            mity.setIsContainFreight(isContainFreight);
        }

        if (null != merchantSettlementDate && !merchantSettlementDate.trim().isEmpty()) {
            mity.setMerchantSettlementDate(Integer.valueOf(merchantSettlementDate));
        }
        if (null != settlementBankName && !settlementBankName.trim().isEmpty()) {
            mity.setSettlementBankName(settlementBankName);
        }
        if (null != settlementCardNo && !settlementCardNo.trim().isEmpty()) {
            mity.setSettlementCardNo(settlementCardNo);
        }
        if (null != manageType && !manageType.trim().isEmpty()) {
            mity.setManageType(manageType);
        }
        if (null != orgCode && !orgCode.trim().isEmpty()) {
            mity.setOrgCode(orgCode);
        }
        if (null != status && !status.trim().isEmpty()) {
            mity.setStatus(status);
        }
        String name = SpringSecurityUtils.getLoginUserDetails().getRealName();// 获取修改
        if (null != name && !name.trim().isEmpty()) {
            mity.setUpdateUser(name);
        }

        return mity;
    }

    /**
     * 得到最大的ID
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMaxId", method = RequestMethod.POST)
    public Response getMaxId(HttpServletRequest request) {
        int id = merchantInforService.getMaxId();
        return Response.success("获取数据成功！",String.valueOf(id+1));
    }
}
