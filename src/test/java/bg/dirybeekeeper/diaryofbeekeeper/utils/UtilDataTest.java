package bg.dirybeekeeper.diaryofbeekeeper.utils;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.*;
import bg.dirybeekeeper.diaryofbeekeeper.repository.BeehiveRepository;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UtilDataTest {

    private final UserRepository userRepository;
    private final BeehiveRepository beehiveRepository;

    public UtilDataTest(UserRepository userRepository, BeehiveRepository beehiveRepository) {
        this.userRepository = userRepository;
        this.beehiveRepository = beehiveRepository;
    }

    public UserEntity createUser() {
        UserEntity user = new UserEntity();

        user.setUsername("claymore");
        user.setFirstName("Galin");
        user.setLastName("Manov");
        user.setEmail("galin@gmail.com");
        user.setPassword("12345");
        user.setEnabled(false);

        user.setVerificationCode("2J1LsD");

        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnum.ADMIN);

        user.setRoles(List.of(role));

        return userRepository.save(user);
    }

    public BeehiveEntity createBeehive() {
        BeehiveEntity beehive = new BeehiveEntity();
        beehive.setCurrentNumber(2);

        QueenEntity queen = new QueenEntity();
        queen.setQueenType(QueenTypeEnum.Yellow);
        queen.setQueenBorn(LocalDate.now());

        beehive.setQueen(queen);
        beehive.setLength(1);
        beehive.setHigh(1);
        beehive.setWidth(1);
        beehive.setLastNutrition(LocalDate.now());
        beehive.setCapacity((byte) 10);
        beehive.setStatus(BeehiveStatusEnum.WORKING);

        return beehiveRepository.save(beehive);
    }

    public void clearDatabase() {
        userRepository.deleteAll();
        beehiveRepository.deleteAll();
    }
}
