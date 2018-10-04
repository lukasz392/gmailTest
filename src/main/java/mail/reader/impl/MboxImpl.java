package mail.reader.impl;

import mail.reader.Mail;
import mail.reader.Mbox;

import java.util.List;
import java.util.stream.Collectors;

public class MboxImpl implements Mbox {

    private final GService service;

    public MboxImpl() {
        service = GService.getInstance();
    }

    @Override
    public List<Mail> getEmails() {
        try {
            List<String> ids = service.listAllMessagesIds();
            return ids.stream().map(id -> service.getMailForId(id)).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new RuntimeException("Cannot get emails", exception);
        }
    }

}
