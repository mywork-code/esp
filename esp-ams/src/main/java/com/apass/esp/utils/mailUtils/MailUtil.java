package com.apass.esp.utils.mailUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by xianzhi.wang on 2017/5/18.
 */
public class MailUtil {

    public boolean sendTextMail(MailSenderInfo mailInfo) {
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            Address from = new InternetAddress(mailInfo.getFromAddress());
            mailMessage.setFrom(from);
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            mailMessage.setSubject(mailInfo.getSubject());
            mailMessage.setSentDate(new Date());
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

        return false;
    }


    public static void main(String[] args) {
        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        mailSenderInfo.setMailServerHost("SMTP.263.net");
        mailSenderInfo.setMailServerPort("25");
        mailSenderInfo.setValidate(true);
        mailSenderInfo.setUserName("sunchaohai@apass.cn");
        mailSenderInfo.setPassword("sch1234");// 您的邮箱密码
        mailSenderInfo.setFromAddress("sunchaohai@apass.cn");
        mailSenderInfo.setSubject("接口预警");//邮箱标题
        mailSenderInfo.setToAddress("xujie@apass.cn");
        mailSenderInfo.setContent("1111");
        MailUtil mailUtil = new MailUtil();
        mailUtil.sendTextMail(mailSenderInfo);

    }
}
