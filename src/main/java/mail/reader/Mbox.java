package mail.reader;

import javax.mail.internet.MimeMessage;
import java.util.List;

public interface Mbox {

    List<MimeMessage> getEmails();


}
