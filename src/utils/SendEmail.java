package utils;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mongodb.DBObject;

public class SendEmail {
	private static String username = "kasukasufr@gmail.com"; 
	private static String password = "usakusak";
	private static String from="kasukasufr@gmail.com"; 
	static {
		try{
			DBObject mail=services.config.Config.getMailConfiguration();
			username=mail.get("mail").toString();
			from=mail.get("mail").toString();
			password=mail.get("pass").toString();
			if(username ==null || from == null || password ==null)
				throw new Exception("Mail not initialized");
		}catch(Exception e){
			username = "kasukasufr@gmail.com"; 
			password = "usakusak";
			from="kasukasufr@gmail.com"; 
		}
	}


	public static void sendMail(String to, String subject, String contenu) {
		Properties props;

		props=services.config.Config.getMailConfigurationProperties();
		if(props == null){
			// Get a Properties object
			props = System.getProperties();
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.store.protocol", "pop3");
			props.put("mail.transport.protocol", "smtp");
		}
		try{
			Session session = Session.getDefaultInstance(props,new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);}});

			// -- Create a new message --
			Message msg = new MimeMessage(session);

			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to,false));

			msg.setSubject(subject);
			msg.setText(contenu);
			msg.setSentDate(new Date());
			Transport.send(msg);
		}catch (MessagingException e){ System.out.println("Erreur d'envoi, cause: " + e);}
	}

	public static void main(String[] args) {
		sendMail("kasukasufr@gmail.com", "subject", "contenu"); }
}