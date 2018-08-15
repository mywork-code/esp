package com.apass.esp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import com.apass.esp.domain.entity.mail.FileBodyInfo;
import com.apass.esp.domain.entity.mail.MailDataInfo;
import com.apass.esp.domain.entity.mail.MailPersonalInfo;
import com.apass.gfb.framework.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SendMailClient {
    
	private static final int PORT = 25; // 端口号
	private static final String HOST = "smtp.263.net"; // 服务器地址

	public boolean sendMail(MailDataInfo mailDataEntity) throws BusinessException {
		boolean isFlag = false;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", HOST);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getInstance(props);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(mailDataEntity.getSender().getMailAddress(),
					mailDataEntity.getSender().getMailName()));
			List<InternetAddress> internetAddressTo = new ArrayList<InternetAddress>();
			for (MailPersonalInfo toMail : mailDataEntity.getToMails()) {
				if (StringUtils.isNotBlank(toMail.getMailName())) {
					InternetAddress address = new InternetAddress(toMail.getMailAddress(), toMail.getMailName());
					internetAddressTo.add(address);
				} else {
					InternetAddress address = new InternetAddress(toMail.getMailAddress());
					internetAddressTo.add(address);
				}
			}
			List<InternetAddress> internetAddressCc = new ArrayList<InternetAddress>();
			for (MailPersonalInfo ccMail : mailDataEntity.getCcMails()) {
				if (StringUtils.isNotBlank(ccMail.getMailName())) {
					InternetAddress address = new InternetAddress(ccMail.getMailAddress(), ccMail.getMailName());
					internetAddressCc.add(address);
				} else {
					InternetAddress address = new InternetAddress(ccMail.getMailAddress());
					internetAddressCc.add(address);
				}
			}
			internetAddressCc.add(new InternetAddress("lixining@apass.cn"));
			
			InternetAddress[] addressTo = null;
			InternetAddress[] addressCc = null;
			// 不做环境区分，按实际人员发送
//			if(systemEnvConfig.isPROD()){
			    addressTo = internetAddressTo.toArray(new InternetAddress[] {});
                addressCc = internetAddressCc.toArray(new InternetAddress[] {});
//			} else {
//			    addressTo = InternetAddress.parse("lixining@apass.cn");
//            }
			
			message.addRecipients(Message.RecipientType.TO, addressTo);
			message.addRecipients(Message.RecipientType.CC, addressCc);
			message.setSubject(mailDataEntity.getSubject());
			message.addHeader("charset", "UTF-8");

			/* 添加正文内容 */
			Multipart multipart = new MimeMultipart();
			BodyPart contentPart = new MimeBodyPart();

			contentPart.setContent(mailDataEntity.getContent(), "text/html;charset=UTF-8");

			multipart.addBodyPart(contentPart);

			for (FileBodyInfo file : mailDataEntity.getFileBodys()) {
				MimeBodyPart fileBody = new MimeBodyPart();
				DataSource source = new ByteArrayDataSource(file.getIs(), "application/msexcel");
				fileBody.setDataHandler(new DataHandler(source));
				// 中文乱码问题
				fileBody.setFileName(MimeUtility.encodeText(file.getFileName()));
				multipart.addBodyPart(fileBody);
			}

			message.setContent(multipart);
			message.setSentDate(new Date());
			message.saveChanges();
			Transport transport = session.getTransport("smtp");

			transport.connect(HOST, PORT, mailDataEntity.getSender().getMailAddress(),
					mailDataEntity.getSender().getMailPassword());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			isFlag = true;

		} catch (Exception e) {
			throw new BusinessException("邮件发送失败", e);
		}
		return isFlag;
	}
}
