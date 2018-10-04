package mail.reader.impl;

import mail.reader.Mail;

public class MailImpl implements Mail {

    private String message;
    private String title;

    public MailImpl(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return "";
        }
        return message;
    }

    @Override
    public String getAttatchments() {
        return null;
    }

    @Override
    public String toString() {
        return "MailImpl{" +
                "message='" + message + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
