package mail.reader.impl;

import mail.reader.Mbox;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

public class MboxImpl implements Mbox {

    private final GService service;

    public MboxImpl() {
        service = GService.getInstance();
    }

    @Override
    public List<MimeMessage> getEmails() {
        try {
            List<String> ids = service.listAllMessagesIds();
            System.out.println("found " + ids.size() + " emails");
            return ids.stream().map(id -> service.getMailForId(id)).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new RuntimeException("Cannot get emails", exception);
        }
    }

}
