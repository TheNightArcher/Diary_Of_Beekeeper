package bg.dirybeekeeper.diaryofbeekeeper.security;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.*;
import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeekeeperUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private BeekeeperUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new BeekeeperUserDetailsService(
                mockUserRepository
        );
    }

    @Test
    void testLoadUserByUsername_UserExist() {

        // arrange
        UserEntity testUserEntity = new UserEntity();
        testUserEntity.setUsername("claymore");
        testUserEntity.setFirstName("Galin");
        testUserEntity.setLastName("Manov");
        testUserEntity.setEmail("galin@gmail.com");
        testUserEntity.setPassword("12345");
        testUserEntity.setEnabled(true);
        testUserEntity.setVerificationCode(null);

        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnum.ADMIN);

        testUserEntity.setRoles(List.of(role));

        BeehiveEntity beehive = new BeehiveEntity();
        beehive.setCurrentNumber(1);

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

        testUserEntity.setBeehives(Set.of(beehive));

        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).
                thenReturn(Optional.of(testUserEntity));

        // act
        BeekeeperUserDetails userDetails = (BeekeeperUserDetails)
                toTest.loadUserByUsername(testUserEntity.getUsername());

        // assert
        Assertions.assertEquals(testUserEntity.getUsername(),
                userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());

        var authorities = userDetails.getAuthorities();

        Assertions.assertEquals(1, authorities.size());
    }

    @Test
    void testLoadUserByUsername_UserDoseNotExist() {
        // arrange
        // NOTE: No need to arrange anything, mocks return empty optionals.

        // act && assert
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("non-existant@example.com")
        );
    }
}
