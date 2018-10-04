package mail.reader.impl;

import mail.reader.Mail;

public class MailImpl implements Mail {

    private String message;

    public MailImpl(String message) {
        this.message = message;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getAttatchments() {
        return null;
    }
}
