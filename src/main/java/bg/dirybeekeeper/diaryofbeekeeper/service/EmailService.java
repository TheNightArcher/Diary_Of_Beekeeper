package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserEntity;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;

    public EmailService(TemplateEngine templateEngine, MessageSource messageSource, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }

    public void sendSendNewPassword(UserEntity user, Locale resolveLocale) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("diaryofbeekeeper@gmail.com");
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject(getEmailSubject(resolveLocale));
            mimeMessageHelper.setText(generateMessageContentResetPassword(resolveLocale, user), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(UserEntity user, Locale preferredLocale) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("diaryofbeekeeper@gmail.com");
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject(getEmailSubject(preferredLocale));
            mimeMessageHelper.setText(generateMessageContentRegister(preferredLocale, user), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getEmailSubject(Locale locale) {
        return messageSource.getMessage(
                "registration_subject",
                new Object[0],
                locale);
    }

    private String generateMessageContentResetPassword(Locale locale, UserEntity user) {

        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("username", user.getUsername());
        context.setVariable("randomPassword", user.getPassword());
        context.setVariable("link", "/change?code=");

        return templateEngine.process("email/forgot-password", context);
    }

    private String generateMessageContentRegister(Locale locale, UserEntity user) {

        Context context = new Context();
        context.setLocale(locale);
        context.setVariable("username", user.getUsername());
        context.setVariable("link", "/verify?code=" + user.getVerificationCode());

        return templateEngine.process("email/registration", context);
    }
}
