package com.apass.esp.utils.mailUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
public class MailUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);


//    public static void main(String[] args) {
//        MailUtil mailUtil = new MailUtil();
//        MailSenderInfo mailSenderInfo = new MailSenderInfo();
//        mailSenderInfo.setMailServerHost("SMTP.263.net");
//        mailSenderInfo.setMailServerPort("25");
//        mailSenderInfo.setValidate(true);
//        mailSenderInfo.setUserName("itsupport@apass.cn");
//        mailSenderInfo.setPassword("support0511");// 您的邮箱密码
//        mailSenderInfo.setFromAddress("itsupport@apass.cn");
//        mailSenderInfo.setSubject("次");//邮箱标题
//        mailSenderInfo.setContent("1111");
//        mailSenderInfo.setToAddress("wangxianzhi1211@163.com");
//        mailSenderInfo.setCcAddress("529230345@qq.com");
//       // mailSenderInfo.setAttachFileNames("111");
//        mailUtil.sendTextMail(mailSenderInfo);
//    }

    public boolean sendTextMail(MailSenderInfo mailInfo) {
        Properties pro = mailInfo.getProperties();
        Session sendMailSession = Session.getDefaultInstance(pro, MyAuthenticator.getInstance());
        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            Address from = new InternetAddress(mailInfo.getFromAddress());
            mailMessage.setFrom(from);

            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            if (mailInfo.getCcAddress() != null) {
                mailMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailInfo.getCcAddress()));
            }
            mailMessage.setSubject(mailInfo.getSubject());
            mailMessage.setSentDate(new Date());
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            LOGGER.error("send email error ..",ex);
        }

        return false;
    }

}
