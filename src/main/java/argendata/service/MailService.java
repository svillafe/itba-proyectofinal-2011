package argendata.service;

import java.util.Date;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MailService {

	public static final String PROTOCOL = "smtp";
	public static final String AUTH = "true";
	
	private argendata.util.Properties properties;
	
	private final Logger logger = Logger.getLogger(MailService.class);
	
	@Autowired
	public MailService(argendata.util.Properties properties){
		this.properties= properties;
		
	}


	public void sendMail(final String addressDest, final String subject,
			final String content, final String typeContent) {

		Runnable r = new Runnable() {

			public void run() {
				try {
					boolean debug = true;

					java.util.Properties props = new java.util.Properties();
					
					props.put("mail.smtp.starttls.enable", properties.getMailStartTLSEnable());
					props.put("mail.transport.protocol", properties.getMailProtocol());
					props.put("mail.smtp.host", properties.getSmtpServer());
					props.put("mail.smtp.port", properties.getSmtpPort());
					props.put("mail.smtp.auth", AUTH);

				
					Session session = Session.getDefaultInstance(props,
							new SMTPAuthenticator());
					session.setDebug(debug);

					Message msg = new MimeMessage(session);

					InternetAddress addressFrom = new InternetAddress(
							properties.getMailAddressFrom());

					msg.setFrom(addressFrom);
					InternetAddress addressTo = new InternetAddress(addressDest);
					msg.setRecipient(Message.RecipientType.TO, addressTo);

					msg.setSubject(subject);

					msg.setContent(content, typeContent);
					msg.setSentDate(new Date());
					Transport.send(msg);
				} catch (Exception e) {
					logger.error("No se pudo mandar el mail a "+ addressDest, e);
				}
			}
		};

		new Thread(r).start();
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = properties.getMailUsername();
			String password = properties.getMailPassword();
			return new PasswordAuthentication(username, password);
		}
	}

}
