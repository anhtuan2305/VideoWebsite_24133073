package vn.trananhtuan.video.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServiceImpl_24133073
        implements IEmailService_24133073 {

    private static final SecureRandom RANDOM =
            new SecureRandom();

    private final Properties mailProperties;

    public EmailServiceImpl_24133073() {
        this.mailProperties = loadMailProperties();
    }

    @Override
    public String generateOTP() {

        int otpNumber =
                100000 + RANDOM.nextInt(900000);

        return String.valueOf(otpNumber);
    }

    @Override
    public boolean sendOTP(
            String recipientEmail,
            String otpCode) {

        if (recipientEmail == null
                || recipientEmail.isBlank()
                || otpCode == null
                || otpCode.isBlank()
                || mailProperties.isEmpty()) {

            return false;
        }

        String senderEmail =
                mailProperties.getProperty(
                        "mail.username"
                );

        String appPassword =
                mailProperties.getProperty(
                        "mail.password"
                );

        if (senderEmail == null
                || senderEmail.isBlank()
                || appPassword == null
                || appPassword.isBlank()) {

            return false;
        }

        Session session = Session.getInstance(
                mailProperties,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication
                            getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                senderEmail,
                                appPassword
                        );
                    }
                }
        );

        try {
            Message message = new MimeMessage(session);

            message.setFrom(
                    new InternetAddress(senderEmail)
            );

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(
                            recipientEmail.trim()
                    )
            );

            message.setSubject(
                    "Mã OTP đăng ký Video Website"
            );

            String content = """
                    Xin chào,

                    Mã OTP xác nhận đăng ký của bạn là: %s

                    Mã OTP có hiệu lực trong 5 phút.
                    Không cung cấp mã này cho người khác.

                    Trân trọng,
                    Video Website - TranAnhTuan - 24133073
                    """.formatted(otpCode);

            message.setText(content);

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Properties loadMailProperties() {

        Properties properties = new Properties();

        try (InputStream inputStream =
                     getClass()
                             .getClassLoader()
                             .getResourceAsStream(
                                     "mail.properties"
                             )) {

            if (inputStream == null) {
                System.err.println(
                        "Không tìm thấy mail.properties"
                );

                return properties;
            }

            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}