package com.apass.esp.service.message;

import com.apass.esp.domain.entity.MessageListener;
import com.apass.esp.domain.entity.ProCouponTaskEntity;
import com.apass.esp.domain.entity.mail.FileBodyInfo;
import com.apass.esp.domain.entity.mail.MailDataInfo;
import com.apass.esp.domain.entity.mail.MailPersonalInfo;
import com.apass.esp.mapper.MessageListenerMapper;
import com.apass.esp.service.SendMailClient;
import com.apass.esp.utils.ResponsePageBody;
import com.apass.gfb.framework.exception.BusinessException;
import com.apass.gfb.framework.utils.BaseConstants;
import com.apass.gfb.framework.utils.DateFormatUtil;
import com.apass.gfb.framework.utils.ExcelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.terracotta.modules.ehcache.ToolkitInstanceFactoryImpl.LOGGER;

@Service
public class MessageListenerService {
	@Autowired
	private MessageListenerMapper messageListenerMapper;
	@Autowired
	private SendMailClient sendMailClient;
	/**
	 * 获取反馈信息列表
	 */
	public ResponsePageBody<MessageListener> getmessageListenerPageList(MessageListener query) throws BusinessException{
		ResponsePageBody<MessageListener> pageBody = new ResponsePageBody<MessageListener>();
		List<MessageListener> backList = messageListenerMapper.getMessageListenerListPage(query);
		if(CollectionUtils.isEmpty(backList)){
			pageBody.setTotal(0);
		}else{
			Integer count =messageListenerMapper.getMessageListenerListPageCount(query);
			pageBody.setTotal(count);
		}
		for(MessageListener m:backList){
			m.setCreateDateString(DateFormatUtil.datetime2String(m.getCreatedTime()));
		}
		pageBody.setRows(backList);
		pageBody.setStatus(BaseConstants.CommonCode.SUCCESS_CODE);
		return pageBody;
	}


	public void sentMail(List<ProCouponTaskEntity> taskEntityList, String[] reciveArr,String mailAddress,String mailName,String mailPassword) throws BusinessException {
		//获取要发送数据的io流
		InputStream in = this.getInputStreamFromList(taskEntityList);
		//发送邮件
		String dateString = DateFormatUtil.dateToString(new Date(), DateFormatUtil.YYYY_MM_DD);

		MailDataInfo mailDataDto = new MailDataInfo();
		mailDataDto.setSubject(dateString + "_房易贷券使用领取情况统计_领取数量");

		// 邮件主题
		mailDataDto.setSender(new MailPersonalInfo(mailAddress, mailName, mailPassword));
		// 收件人
		for(String mail : reciveArr){
			mailDataDto.addToMails(new MailPersonalInfo(mail));
		}

		// 邮件正文
		mailDataDto.setContent("你好：<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此报表每天8：00发出;"
				+ "<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;初次发送时请抽查数据验证;"
				+ "<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据详情见附件。");

		// 附件1
		FileBodyInfo filebody = new FileBodyInfo();
		filebody.setFileName(dateString + "_房易贷券使用领取情况统计_领取数量.xls");
		filebody.setIs(in);


		mailDataDto.addFileBody(filebody);

		sendMailClient.sendMail(mailDataDto);

	}

	private InputStream getInputStreamFromList(List<ProCouponTaskEntity> taskEntityList) {
		// 建立新HSSFWorkbook对象
		XSSFWorkbook wb = new XSSFWorkbook();

		// 建立新的sheet对象
		Sheet sheet = wb.createSheet("sheet1");
		// 封装sheet 数据

		String[] titleArr = {"日期","扫码下载APP点击次数","扫码领券人数","身份认证券发放人数","银行卡认证券发放人数","放款成功券发放人数"};
		buildSheet(sheet, taskEntityList, titleArr);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			wb.write(baos);
			baos.flush();
			byte[] bt = baos.toByteArray();
			is = new ByteArrayInputStream(bt, 0, bt.length);

		} catch (Exception e1) {
			LOGGER.error("", e1);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				LOGGER.error("", e);
			}
		}
		return is;
	}

	private void buildSheet(Sheet sheet, List<ProCouponTaskEntity> taskEntityList,String[] titleArr) {
		for(int i=0; i<titleArr.length; i++){
			ExcelUtils.setCellValue(sheet, 0, i, titleArr[i]);
		}

		for (int i = 0; i < taskEntityList.size(); i++) {
			ProCouponTaskEntity dataDto = taskEntityList.get(i);
			ExcelUtils.setCellValue(sheet, i + 1, 0, dataDto.getDate());
			ExcelUtils.setCellValue(sheet, i + 1, 1, dataDto.getClickCount()+"");
			ExcelUtils.setCellValue(sheet, i + 1, 2, dataDto.getCount()+"");
			ExcelUtils.setCellValue(sheet, i + 1, 3, dataDto.getSfzrzCount()+"");
			ExcelUtils.setCellValue(sheet, i + 1, 4, dataDto.getYhkrzCount()+"");
			ExcelUtils.setCellValue(sheet, i + 1, 5, dataDto.getFkcgCount()+"");
		}
	}
}
