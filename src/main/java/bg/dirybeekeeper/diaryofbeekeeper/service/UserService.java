package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.UserRegisterServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterServiceModel userRegisterServiceModel) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(userRegisterServiceModel.getUsername());

        if (byEmail.isPresent()) {
            throw new RuntimeException("This email is already used.");
        }

        UserEntity user = modelMapper.map(userRegisterServiceModel, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        this.userRepository.save(user);
    }
}
