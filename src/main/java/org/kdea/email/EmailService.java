package org.kdea.email;


import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {
     
    @Autowired
    protected JavaMailSender  mailSender;

    public boolean sendMail(EmailVO email) throws Exception {
        try{
	        MimeMessage msg = mailSender.createMimeMessage();
	 	msg.setFrom("someone@paran.com"); // �۽��ڸ� �����ص� �ҿ������ ������ ������ �߻��Ѵ�
	        msg.setSubject(email.getSubject());
	        msg.setText(email.getContent());
	        msg.setRecipient(RecipientType.TO , new InternetAddress(email.getReceiver()));
	         
	        mailSender.send(msg);
	        return true;
        }catch(Exception ex) {
        	ex.printStackTrace();
        }
        return false;
    }
}