package mail.reader.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.common.collect.Lists;
import mail.reader.GmailQuickstart;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class GService {
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Lists.newArrayList(GmailScopes.GMAIL_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String user = "me";

    private static GService gService;
    private final Gmail service;


    private GService() {
        try {
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception exception) {
            throw new RuntimeException("Cannot create MboxImpl", exception);
        }
    }

    public static GService getInstance() {
        if (gService == null) {
            gService = new GService();
        }
        return gService;
    }

    public List<String> listAllMessagesIds() {
        try {
            return service.users().messages().list(user).execute().getMessages().stream().map(m -> m.getId()).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Cannot list messages ids", e);
        }
    }


    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public MimeMessage getMailForId(String id) {
        try {
            Message msg = service.users().messages().get(user, id).setFormat("raw").execute();
            System.out.println("Got message for id: " + id);
            return getMimeMessage(msg);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get message with id: " + id, e);
        }
    }

    public MimeMessage getMimeMessage(Message msg) {
        try {
            byte[] bytes = Base64.decodeBase64(msg.getRaw());
            Properties prop = new Properties();
            Session session = Session.getDefaultInstance(prop, null);

            return new MimeMessage(session, new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            throw new RuntimeException("Cannot create mime message ", e);
        }
    }
}
