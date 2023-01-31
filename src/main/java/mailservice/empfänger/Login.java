package mailservice.empfänger;

import com.sun.mail.imap.IMAPStore;
import mail.data.ServerData;
import mail.send.MailSender;
import org.jboss.logging.Logger;

import javax.mail.*;
import java.util.Properties;


public class Login {
    private Session mailSession;
    protected IMAPStore imapStore;
    private final Logger logger = Logger.getLogger(Login.class);


    public void senderLogin(String username, String password) {
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

    public void empfaengerLogin(String username, String password) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.port", ServerData.IMAPPORT.getData());
        properties.put("mail.imap.starttls.enable", "true");
        Session mailSession = Session.getDefaultInstance(properties);
        Store store = mailSession.getStore("imaps");
        store.connect(ServerData.IMAPHOST.getData(), username, password);
        this.imapStore = (IMAPStore) store;
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

   /* public int tryToAuthenticate(String username, String password) {
        int exitcode = 0;
        login(username, password);
        try {
            if (getMailSession() == null) {
                System.out.println("Bitte erst anmelden!");
            }
            MimeMessage msg = new MimeMessage(mailSession);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8-bit");

            msg.setFrom(new InternetAddress(username, "Authenticator"));
            msg.setSubject("Erfolgreich angemeldet", "UTF-8");
            //msg.setText(message, "UTF-8");

            msg.setContent("Erfolgreich in der Kunden-Datenbank angemeldet", "text/html");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username, false));
            System.out.println("Versende Mail....");
            Transport.send(msg);
            System.out.println("Mail Versendet!");
            return exitcode;
        } catch (AuthenticationFailedException e) {
            System.out.println("Authentication fehlgeschlagen");
            return exitcode = 1;
        } catch (MessagingException e) {
            return exitcode = 2;
        } catch (UnsupportedEncodingException e) {
            return exitcode = 3;
        }
    }*/


    public Session getMailSession() {
        return mailSession;
    }

    public void setMailSession(Session mailSession) {
        this.mailSession = mailSession;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }


}
