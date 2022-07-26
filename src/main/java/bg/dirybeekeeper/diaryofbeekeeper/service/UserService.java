package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.UserRegisterServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    public void registerUser(UserRegisterServiceModel userRegisterServiceModel, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        Optional<UserEntity> byEmail = userRepository.findByEmail(userRegisterServiceModel.getUsername());

        if (byEmail.isPresent()) {
            throw new RuntimeException("This email is already used.");
        }

        UserEntity user = modelMapper.map(userRegisterServiceModel, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        String randomCode = RandomString.make(60);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        this.userRepository.save(user);

        sendVerificationEmail(user, siteURL);
    }

    public void sendVerificationEmail(UserEntity user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "diaryofbeekeeper@gmail.com";
        String senderName = "Diary Of Beekeeper";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "The Diary Of Beekeeper";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);


        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    public boolean verify(String verificationCode) {
        UserEntity user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }

    public void addBeehive(BeehiveEntity beehive, BeekeeperUserDetails userDetails) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(userDetails.getUsername());

        UserEntity user = modelMapper.map(userOpt, UserEntity.class);
        user.getBeehives().add(beehive);

        userRepository.save(user);
    }
}
