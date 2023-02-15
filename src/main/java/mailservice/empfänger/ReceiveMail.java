package mailservice.empfänger;

import com.sun.mail.imap.IMAPStore;
import org.jboss.logging.Logger;

import javax.mail.*;
import java.util.Properties;


public class ReceiveMail {

    protected IMAPStore imapStore;
    private final Logger logger = Logger.getLogger(ReceiveMail.class);


    public void login(String username, String password) throws MessagingException {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.port", ServerData.IMAPPORT.getData());
            properties.put("mail.imap.starttls.enable", "true");
            Session mailSession = Session.getDefaultInstance(properties);
            Store store = mailSession.getStore("imaps");
            store.connect(ServerData.IMAPHOST.getData(), username, password);
            this.imapStore = (IMAPStore) store;

        } catch (Exception e) {
            logger.log(Logger.Level.WARN, "Es ist folgender Fehler beim einloggen in Ihren Mail Account passiert:"
                    + e.getMessage());

        }

    }

    public Message[] checkNewMessages() throws MessagingException {
        if (imapStore == null) {
            throw new IllegalStateException("Zuerst einloggen!");
        }

        Folder mailFolder = imapStore.getFolder("INBOX");
        mailFolder.open(Folder.READ_ONLY);

        System.out.println("Es sind " + mailFolder.getUnreadMessageCount() + " ungelesen");

        Message[] messages = mailFolder.getMessages();

        for (int i = 0; i < messages.length; i++) {
            System.out.println("Datum Uhrzeit" + messages[i].getReceivedDate());
            System.out.println("Von" + messages[i].getFrom());
            System.out.println("Betreff" + messages[i].getSubject());
        }
        return messages;
    }


    public static void main(String[] args) throws MessagingException {
        ReceiveMail receiveMail = new ReceiveMail();
        receiveMail.login("entwicklung@gollenstede-entwicklung.de", "rijdyg-tatquw-8pebzU");

        receiveMail.checkNewMessages();
    }


    public IMAPStore getImapStore() {
        return imapStore;
    }

    public void setImapStore(IMAPStore imapStore) {
        this.imapStore = imapStore;
    }

    public Logger getLogger() {
        return logger;
    }
}
