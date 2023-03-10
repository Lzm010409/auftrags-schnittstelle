package mailservice.empfänger;

import org.jboss.logging.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class SendMail {
    private final Logger logger = Logger.getLogger(SendMail.class);
    private Session mailSession;

    public void login(String username, String password) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", ServerData.SMTPHOST.getData());
        properties.put("mail.smtp.socketFactory.port", ServerData.SMTPPORT.getData());
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", ServerData.SMTPPORT.getData());

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        try {
            this.mailSession = Session.getDefaultInstance(properties, authenticator);
            logger.log(Logger.Level.INFO, "Eingeloggt");
        } catch (Exception e) {
            logger.log(Logger.Level.WARN, "Es ist folgender Fehler beim einloggen in Ihren Mail Account passiert:"
                    + e.getMessage());

        }

    }

    public void sendMail(String senderAdress, String senderName, String receiverAdress, String subject, File file) throws MessagingException, IOException, IllegalStateException {
        if (mailSession == null) {
            throw new IllegalStateException("Erst einloggen!");
        }
        try {
            MimeMessage msg = new MimeMessage(mailSession);
            /*msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8-bit");
*/
            msg.setFrom(new InternetAddress(senderAdress, senderName));
            msg.setReplyTo(InternetAddress.parse(senderAdress, false));
            msg.setSubject(subject, "UTF-8");
            //msg.setText(message, "UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("");
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(file);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            msg.setContent(multipart);
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAdress, false));


            Transport.send(msg);
            logger.log(Logger.Level.INFO, "Mail versendet!");
        } catch (Exception e) {
            logger.log(Logger.Level.WARN, "Es ist ein Fehler beim Versand aufgetreten!" + e.getMessage());
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public Session getMailSession() {
        return mailSession;
    }

    public void setMailSession(Session mailSession) {
        this.mailSession = mailSession;
    }
}
