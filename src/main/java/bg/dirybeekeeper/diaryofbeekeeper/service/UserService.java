package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.UserRegisterServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, EmailService emailService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterServiceModel userRegisterServiceModel, Locale preferredLocale) {

        UserEntity user = modelMapper.map(userRegisterServiceModel, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        String randomCode = RandomString.make(60);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        this.userRepository.save(user);

        emailService.sendVerificationEmail(user, preferredLocale);
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
