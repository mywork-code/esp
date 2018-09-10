package com.apass.esp.service.home;

import com.apass.esp.domain.dto.statement.PAInterfaceDto;
import com.apass.esp.domain.dto.statement.PAInterfaceResponse;
import com.apass.esp.domain.entity.PAUser;
import com.apass.esp.domain.entity.customer.CustomerInfo;
import com.apass.esp.domain.enums.CouponIsDelete;
import com.apass.esp.mapper.PAUserMapper;
import com.apass.esp.service.bill.CustomerServiceClient;
import com.apass.esp.service.common.MobileSmsService;
import com.apass.gfb.framework.environment.SystemEnvConfig;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.*;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PAUserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PAUserService.class);
	private static String adCode = null;
	private static String qianMing = null;
	private static String url = null;

	@Autowired
	private PAUserMapper paUserMapper;
	@Autowired
	private SystemEnvConfig config;
	/**
	 * 验证码工具
	 */
	@Autowired
	private MobileSmsService mobileRandomService;

	@Autowired
	private CustomerServiceClient customerServiceClient;

	public PAUser selectUserByUserId(String userId) {
		return paUserMapper.selectUserByUserId(userId);
	}

	@Transactional(rollbackFor = {Exception.class})
	public int savePAUser(Map<String, Object> paramMap)throws Exception {
		String username = CommonUtils.getValue(paramMap,"username");
		String identity = CommonUtils.getValue(paramMap, "identity");
		String mobile = CommonUtils.getValue(paramMap, "mobile");
		String userId = CommonUtils.getValue(paramMap, "userId");
		String sex = CommonUtils.getValue(paramMap, "sex");
		String ip = CommonUtils.getValue(paramMap,"ip");
		String userAgent = CommonUtils.getValue(paramMap,"userAgent");
		String authCode = CommonUtils.getValue(paramMap,"authCode");
		String smsType = CommonUtils.getValue(paramMap, "smsType");// 验证码短信类型
		if(StringUtils.isEmpty(username)&&isChineseStr(username)){
			throw new RuntimeException("姓名不可为空且必须为汉字");
		}
		if(StringUtils.isAnyBlank(identity)){
			throw new RuntimeException("身份证不可为空");
		}
		if(StringUtils.isAnyBlank(mobile)){
			throw new RuntimeException("手机号不可为空");
		}
//		if(StringUtils.isAnyBlank(userId)){
//			throw new RuntimeException("用户id不可为空");
//		}
		if(StringUtils.isAnyBlank(sex)){
			throw new RuntimeException("性别不可为空");
		}
		//姓名汉语校验
		boolean ifChinese = isChineseStr(username);
		if(!ifChinese){
			throw new RuntimeException("用户名必须为汉字");
		}

		if (StringUtils.isAnyBlank(authCode,smsType)){
			throw new RuntimeException("验证码和验证码类型不可为空");
		}

		LOGGER.info("保存用户信息savePAUser()参数，username:{}," +
				"identity:{},mobile:{},userId:{},sex:{},ip:{}," +
				"userAgent:{},authCode:{},smsType:{}",username,identity,mobile,userId,sex,ip,userAgent,authCode,smsType);
		//判断验证码是否正确
		boolean flag = mobileRandomService.mobileCodeValidate(smsType, mobile, authCode);
		if(!flag){
			throw new RuntimeException("验证码不正确");
		}

		//调用平安接口:如果成功保存用户信息，如果失败返回失败信息
		getAdcodeUrlAndQianMing();
		String sign = MD5Utils.getStingMD5(adCode+qianMing+mobile);
		PAInterfaceDto dto = new PAInterfaceDto();
		dto.setPolicyHolderName(username);
		dto.setAdCode(adCode);
		dto.setMobile(mobile);
		dto.setActivityConfigNum("0");
		dto.setFromIp(ip);
		dto.setUserAgent(userAgent);
		dto.setSign(sign);
		dto.setPolicyHolderSex("1".equals(sex)?"MALE":"FEMALE");
		dto.setPolicyHolderIdCard(identity);

		Map<String, String> resultMap = saveToPAInterface(dto, url);
		if(StringUtils.equalsIgnoreCase(resultMap.get("status"),"FAILED")){
			throw new RuntimeException(resultMap.get("message"));
		}

		PAUser paUser = new PAUser();
		paUser.setUsername(username);
		paUser.setTelephone(mobile);
		paUser.setBirthday(DateFormatUtil.string2date("1900-01-01"));
		paUser.setUserId(Long.valueOf(userId));
		paUser.setSex(Byte.valueOf(sex));
		paUser.setFromIp(ip);
		paUser.setUpdatedTime(new Date());
		paUser.setCreatedTime(new Date());
		paUser.setIsDelete(CouponIsDelete.COUPON_N.getCode());
		paUser.setIdentity(identity);

		return paUserMapper.insertSelective(paUser);
	}

	public Map<String,String> saveToPAInterface(PAInterfaceDto dto,String requestUrl){
		Map<String,String> resultMap = Maps.newHashMap();
		try {
			LOGGER.info("平安投保参数:{}",GsonUtils.toJson(dto));
			String requestJson = GsonUtils.toJson(dto);
			StringEntity entity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
			String responseJson = HttpClientUtils.getMethodPostResponse(requestUrl, entity);
			LOGGER.info("平安投保返回数据:{},手机号:{}",responseJson,dto.getMobile());
			PAInterfaceResponse response = GsonUtils.convertObj(responseJson, PAInterfaceResponse.class);
			resultMap.put("message",response.getMessage());
			resultMap.put("status",response.getStatus());
		}catch (Exception e){
			LOGGER.error("请求失败,Exception:{}",e);
			resultMap.put("status","FAILED");
		}

		return resultMap;
	}

	/**
	 * 是否含中文
	 * @param str
	 * @return
     */
	public static boolean isChineseStr(String str){
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
		char c[] = str.toCharArray();
		for(int i=0;i<c.length;i++){
			Matcher matcher = pattern.matcher(String.valueOf(c[i]));
			if(!matcher.matches()){
				return false;
			}
		}
		return true;
	}

	@Transactional(rollbackFor = {Exception.class})
	public Integer addPaUserV2(Map<String, Object> paramMap) throws BusinessException {
		String userId = CommonUtils.getValue(paramMap, "userId");
		String mobile = CommonUtils.getValue(paramMap, "mobile");
		String ip = CommonUtils.getValue(paramMap, "ip");
		String userAgent = CommonUtils.getValue(paramMap, "userAgent");
		String agreeFlag = CommonUtils.getValue(paramMap, "agreeFlag");//0-未同意，1-已同意，-1：请求平安接口失败
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(mobile)
				|| StringUtils.isEmpty(agreeFlag)) {
			throw new BusinessException("参数不合法！");
		}
		PAUser paUser = paUserMapper.selectUserByUserId(userId);
		if (paUser != null && paUser.getAgreeFlag().intValue() == 1) {
			throw new BusinessException("赠险每人仅限领取一份，请勿重复领取。");
		}

		PAUser paUserEntity = new PAUser();
		//调远程接口，获取identity
		CustomerInfo customerInfo = customerServiceClient.getDouDoutCustomerInfo(mobile);
		if(customerInfo == null){
			customerInfo = customerServiceClient.getFydCustomerInfo(mobile);
		}
		if(customerInfo != null){
			String identityNo = customerInfo.getIdentityNo();
			paUserEntity.setUsername(customerInfo.getRealName());
			if(StringUtils.isNotEmpty(identityNo)){
				String sexStr = CommonUtils.getIdentityGender(identityNo);
				paUserEntity.setSex(sexStr == "女"?Byte.valueOf("0"):Byte.valueOf("1"));
				paUserEntity.setAge(CommonUtils.getAge(identityNo));
				paUserEntity.setBirthday(DateFormatUtil.string2date(CommonUtils.getIdentityBirth(identityNo),"yyyyMMdd"));
				paUserEntity.setIdentity(identityNo);
			}
		}

		paUserEntity.setTelephone(mobile);
		paUserEntity.setUserId(Long.valueOf(userId));
		paUserEntity.setFromIp(ip);
		paUserEntity.setUserAgent(userAgent);
		paUserEntity.setAgreeFlag(Byte.valueOf(agreeFlag));
		paUserEntity.setUpdatedTime(new Date());
		paUserEntity.setCreatedTime(new Date());
		paUserEntity.setIsDelete(CouponIsDelete.COUPON_N.getCode());
		return paUserMapper.insertSelective(paUserEntity);
	}

	public boolean saveToPAInterface(PAUser paUser) {
		String mobile = paUser.getTelephone();
		getAdcodeUrlAndQianMing();
		String sign = MD5Utils.getStingMD5(adCode+qianMing+mobile);
		PAInterfaceDto dto = new PAInterfaceDto();
		dto.setPolicyHolderName(paUser.getUsername());
		dto.setAdCode(adCode);
		dto.setMobile(mobile);
		dto.setActivityConfigNum("0");
		dto.setFromIp(paUser.getFromIp());
		dto.setUserAgent(paUser.getUserAgent());

		dto.setSign(sign);
		dto.setPolicyHolderSex("1".equals(paUser.getSex())?"MALE":"FEMALE");
		dto.setPolicyHolderIdCard(paUser.getIdentity());

		Map<String, String> resultMap = saveToPAInterface(dto, url);

		return "SUCCEEDED".equalsIgnoreCase(resultMap.get("status"))?true:false;
	}

	private void getAdcodeUrlAndQianMing() {
		if(config.isPROD()){
			adCode = "0f8f560b";
			url = "http://xbbapi.data88.cn/insurance/doInsure";
			qianMing = "bc57981d0419c8a70f554db7c268c1a8";
		}else if (config.isUAT()){
			adCode = "1ae265f6";
			url = "http://xbbstagingapi.data88.cn/insurance/doInsure";
			qianMing = "f82caa903a85b858278a9da5f3fc528a";
		}
	}

	public List<PAUser> selectUserByRangeDate(String startDate, String endDate) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("startDate",startDate);
		paramMap.put("endDate",endDate);

		return paUserMapper.selectUserByRangeDate(paramMap);
	}

	public void updateSelectivePAUser(PAUser paUser) {
		paUserMapper.updateByPrimaryKeySelective(paUser);
	}
}
