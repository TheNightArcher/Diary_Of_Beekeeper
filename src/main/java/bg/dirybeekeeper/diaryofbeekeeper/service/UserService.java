package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserRoleEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserRoleEnum;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.UserRegisterServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.UsersView;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.UserBeehiveDetailsView;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.UserBeehivesView;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BeehiveService beehiveService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, EmailService emailService, BeehiveService beehiveService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.beehiveService = beehiveService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterServiceModel userRegisterServiceModel, Locale preferredLocale) {

        UserEntity user = modelMapper.map(userRegisterServiceModel, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        String randomCode = RandomString.make(60);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnum.USER);

        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(role);

        user.setRoles(roles);

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

    public Page<UserBeehivesView> findMyBeehives(String username, Pageable pageable) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    List<UserBeehivesView> myBeehives = new ArrayList<>();

                    for (BeehiveEntity beehive : user.getBeehives()) {
                        myBeehives.add(new UserBeehivesView(beehive.getId(),
                                beehive.getCurrentNumber(),
                                beehive.getCapacity()));
                    }

                    myBeehives.sort(Comparator.comparingInt(UserBeehivesView::getCurrentNumber));

                    int start = (int) pageable.getOffset();
                    int end = Math.min((start + pageable.getPageSize()), myBeehives.size());

                    return (Page<UserBeehivesView>) new PageImpl<>(myBeehives.subList(start, end), pageable, myBeehives.size());
                })
                .orElseThrow();
    }

    public UserBeehiveDetailsView findBeehiveDetails(String username, Long beehiveId) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    BeehiveEntity currentBeehive = user.getBeehives()
                            .stream()
                            .filter(b -> b.getId().equals(beehiveId))
                            .findFirst()
                            .orElse(null);

                    assert currentBeehive != null;
                    return new UserBeehiveDetailsView(
                            currentBeehive.getId(),
                            currentBeehive.getCurrentNumber(),
                            currentBeehive.getQueen().getQueenType(),
                            currentBeehive.getQueen().getQueenBorn(),
                            currentBeehive.getLength(),
                            currentBeehive.getHigh(),
                            currentBeehive.getWidth(),
                            currentBeehive.getLastNutrition(),
                            currentBeehive.getCapacity(),
                            currentBeehive.getStatus()
                    );
                }).orElse(null);
    }

    public Page<UsersView> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(u -> modelMapper.map(u, UsersView.class));

    }

    public void deleteUserBeehiveById(String username, Long id) {
        Optional<UserEntity> findUser = userRepository.findByUsername(username);

        if (findUser.isPresent()) {
            UserEntity user = modelMapper.map(findUser, UserEntity.class);

            BeehiveEntity beehive = beehiveService.findBeehive(id);

            for (BeehiveEntity element : user.getBeehives()) {

                if (element.getId().equals(beehive.getId())) {
                    user.getBeehives().remove(element);
                    break;
                }
            }

            userRepository.save(user);
        }
    }

    public void editUserBeehiveById(String username, BeehiveEntity beehive) {
        Optional<UserEntity> findUser = userRepository.findByUsername(username);

        if (findUser.isPresent()) {
            UserEntity user = modelMapper.map(findUser, UserEntity.class);

            for (BeehiveEntity element : user.getBeehives()) {

                if (element.getId().equals(beehive.getId())) {
                    user.getBeehives().remove(element);
                    user.getBeehives().add(beehive);
                }

                userRepository.save(user);
            }
        }
    }

    @Scheduled(cron = "0 0 00 * * *")
    private void deleteAllEnabledUsers() {
        userRepository.findAll()
                .stream()
                .filter(u -> !u.isEnabled())
                .forEach(user -> userRepository.deleteById(user.getId()));
    }
}
