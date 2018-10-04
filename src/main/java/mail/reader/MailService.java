package mail.reader;

import mail.reader.impl.MboxImpl;

import java.util.List;

public class MailService {

    public MailService() {
        Mbox mailBox = new MboxImpl();
        List<Mail> emails = mailBox.getEmails();
        System.out.println("ilość maili: " + emails.size());
        emails.forEach(m -> System.out.println(m.getMessage() + "\n\n-----------------\n"));
    }
}
