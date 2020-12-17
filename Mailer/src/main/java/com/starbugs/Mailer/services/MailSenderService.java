package com.starbugs.Mailer.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${client.host}")
	private String clientHost;
	
	public MailSenderService() {
	}
	
	public void sendVerificationEmail(VerificationMailDetails details) throws Exception {
		if(details != null && !details.getUsername().isEmpty() && !details.getToken().isEmpty() && !details.getEmail().isEmpty()) {
			String HTMLTemplate = "<h3>Hello " + details.getUsername() + ",</h3>" + "<br />" +
					"<p>we are very pleased that your company decided to make use of our unique solution to enhance the productivity and quality of your services. \n " + "</p>"
					+ "<p>You are one step close from completeing the registeration process of your company, after which your being able to start adding your own services. \n" + "</p>"
					+ "<p>please click on the following link: " + "</p>" + "<a href = \"http://" + this.clientHost + "/users/verify-email/"+ details.getToken() +"\"" +"> Click Here</a></p>"
					+ "<br>" +
					"<p>Make sure to verify your subscription within 5 hours, otherwise you can request another verification link if you didn't make it.</p>" +
					"<p>Thank you for passing by!</p>" + "<br>" +
					"<p>Best regards,<br /> \n" + "StarBugs Enterprise Solutions</p>";
							
					MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
					
					helper.setTo(details.getEmail());
					helper.setSubject("StarBugs Subscription Verification");
					helper.setText(HTMLTemplate, true);
					
					javaMailSender.send(mimeMessage);
		}else {
			throw new Exception("Missing Verification Mail Details!");
		}

		
	}
	
	public void sendResetPasswordEmail(VerificationMailDetails details) throws Exception {
		if(details != null && !details.getUsername().isEmpty() && !details.getToken().isEmpty() && !details.getEmail().isEmpty()) {
			String HTMLTemplate = "<h3>Hello " + details.getUsername() + ",</h3>" + "<br />" +
					"<p>your StarBugs user is ready. \n " + "</p>"
					+ "<p>You are one step close from completeing the registeration process of your company, after which your being able to login to your account. \n" + "</p>"
					+ "<p>please click on the following link to reset your password: " + "</p>" + "<a href = \"http://" + this.clientHost +"/users/reset-password?token="+ details.getToken() +"\"" +"> Click Here</a></p>"
					+ "<br>" +
					"<p>Make sure to verify your subscription within 5 hours, otherwise you can request another verification link if you didn't make it.</p>" +
					"<p>Thank you for passing by!</p>" + "<br>" +
					"<p>Best regards,<br /> \n" + "StarBugs Enterprise Solutions</p>";
							
					MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
					
					helper.setTo(details.getEmail());
					helper.setSubject("StarBugs Password Reset");
					helper.setText(HTMLTemplate, true);
					
					javaMailSender.send(mimeMessage);
		}else {
			throw new Exception("Missing Reset-Password Mail Details!");
		}

		
	}

}
