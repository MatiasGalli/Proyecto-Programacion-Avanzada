package sendMail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMails{

    private static String emailFrom = "stackoverflowhouse@gmail.com";
    private static String passwordFrom = "chayonwbvcqygjqn";
    private String emailTo;
    private String subject;
    private String content;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mMail;

    public SendMails(String emailTo,String subject,String content) {
        this.emailTo = emailTo;
    	this.subject = subject;
    	this.content = content;
    	mProperties = new Properties();
    }

	public void createEmail() {
        
         // Simple mail transfer protocol
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");
        
        mSession = Session.getDefaultInstance(mProperties);
        
        
        try {
        	mMail = new MimeMessage(mSession);
        	mMail.setFrom(new InternetAddress(emailFrom));
        	mMail.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        	mMail.setSubject(subject);
        	mMail.setText(content, "ISO-8859-1", "html");
                     
            
        } catch (AddressException ex) {
            Logger.getLogger(SendMails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SendMails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mMail, mMail.getRecipients(Message.RecipientType.TO));
            mTransport.close();

        } catch (NoSuchProviderException ex) {
            Logger.getLogger(SendMails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SendMails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
