package it.unitn.shoppinglesto.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class MailHelper {
    private Session session;
    private final String host;
    private final String port;
    private final String mail;
    private final String password;
    private final InternetAddress sender;
    private final InternetAddress[] recepients;
    private final String subject;

    private MimeMultipart multipart;


    public MailHelper(String host, String port, String mail, String password, InternetAddress sender, InternetAddress[] recepients, String subject){
        this.host = host;
        this.port = port;
        this.mail = mail;
        this.password = password;
        this.sender = sender;
        this.recepients = recepients;
        this.subject = subject;
        buildGoogleSession();
        multipart = new MimeMultipart();

    }

    /**
     * Configuring the gmail session with properties
     */
    private void buildGoogleSession(){
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.socketFactory.port", port);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.debug", "true");

        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, password);
            }

        });
    }

    /**
     * Adds single MimeBodyPart to MimeMultipart object.
     * @param part single MimeBodyPart object to be added to MimeMultipart.
     * @throws MessagingException
     */
    public void addSinglePartToMultipart(MimeBodyPart part) throws MessagingException {
        multipart.addBodyPart(part);
    }

    /**
     * Creates MimeMessage objects
     * configures it with sender, recepients, subject and date and sends it.
     * @throws MessagingException
     */
    public void sendMessage() throws MessagingException{
        MimeMessage message = new MimeMessage(this.session);
        message.setFrom(this.sender);
        message.setRecipients(Message.RecipientType.TO, this.recepients);
        message.setSubject(this.subject);
        message.setSentDate(new Date());
        message.setContent(this.multipart);
        Transport.send(message);
    }
}
