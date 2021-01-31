package com.emailservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailHandelerServiceImpl implements EmailHandelerService {

  @Autowired
  private JavaMailSender javaMailSender;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(EmailHandelerServiceImpl.class);

  public void sendBasicEmail(String[] to, String subject, String message) throws MessagingException {

	LOGGER.info("Email starts info");
	LOGGER.debug("Email starts info");
	LOGGER.error("Email starts info");
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
	try {
		helper.setText(message, true);
		helper.setTo(to);
		helper.setSubject(subject);
	} catch (javax.mail.MessagingException e) {
		e.printStackTrace();
	} // Use this or above line.
	
	//helper.setFrom(from);
	javaMailSender.send(mimeMessage);
	
    /*SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(to);

    mail.setSubject(subject);
    mail.setText(message,true);
    mail.IsBodyHtml = true;

    javaMailSender.send(mail);*/

  }

  public void sendEmailWithAttachment(String[] to, String subject, String message, String attachmentName, File attachment) throws MessagingException, IOException {

    MimeMessage msg = javaMailSender.createMimeMessage();

    // true = multipart message
    MimeMessageHelper helper;
	try {
		helper = new MimeMessageHelper(msg, true);
	    helper.setTo(to);
	    helper.setSubject(subject);
	    // default = text/plain
	    //helper.setText("Check attachment for image!");

	    // true = text/html
	    helper.setText(message, true);
	    helper.addAttachment(attachmentName, attachment);
	} catch (javax.mail.MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));

    //Resource resource = new ClassPathResource("android.png");
    //InputStream input = resource.getInputStream();

    //ResourceUtils.getFile("classpath:android.png");

    javaMailSender.send(msg);
  }
}
