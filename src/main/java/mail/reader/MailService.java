package mail.reader;

import mail.reader.impl.MboxImpl;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MailService {

    public MailService() {
        Mbox mailBox = new MboxImpl();
        List<MimeMessage> emails = mailBox.getEmails();
        System.out.println("ilość maili: " + emails.size());
        for (MimeMessage message : emails) {
            try {
                System.out.println("Subject: " + message.getSubject());
                Address[] from = message.getFrom();
                String to = null;
                if (from != null) {
                    List<Address> addresses = Arrays.asList(from);
                    to = addresses.stream().map(a -> {
                        if (a != null) {
                            return a.toString();
                        } else {
                            return "";
                        }
                    }).collect(Collectors.joining(" | "));
                }
                if (to == null) {
                    to = "";
                }
                System.out.println("From: " + message.getFrom());
                System.out.println("To: " + to);
                String content = String.valueOf(message.getContent());
                String show = content.length() > 101 ? content.substring(0, 100) : content;
                System.out.println("First 100 signs of message: " + show);
            } catch (Exception e) {
                throw new RuntimeException("Cannot get message details", e);
            }
        }
    }
}
