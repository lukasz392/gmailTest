package mail.reader.impl;

import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import mail.reader.Mail;

public class MailImpl implements Mail {

    private final String messageBody;

    public MailImpl(Message message) {
        MessagePart payload = message.getPayload();
        messageBody = StringUtils.newStringUtf8(Base64.decodeBase64(payload.getParts().get(0).getBody().getData()));

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getMessage() {
        return messageBody;
    }

    @Override
    public String getAttatchments() {
        return null;
    }
}
