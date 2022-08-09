package bg.dirybeekeeper.diaryofbeekeeper.service;


import bg.dirybeekeeper.diaryofbeekeeper.model.binding.ForgotPasswordBindingModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.*;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private BeehiveService beehiveService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService serviceTest;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        serviceTest = new UserService(userRepository, emailService, beehiveService, modelMapper, passwordEncoder);

        user = new UserEntity();

        user.setUsername("claymore");
        user.setFirstName("Galin");
        user.setLastName("Manov");
        user.setEmail("galin@gmail.com");
        user.setPassword("12345");
        user.setEnabled(false);
        user.setVerificationCode("123");

        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnum.ADMIN);

        user.setRoles(List.of(role));

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

        user.setBeehives(Set.of(beehive));
    }

    @DisplayName("JUnit test setUserNewPassword method")
    @Test
    void testSetUserNewPassword_ShouldReturnNewPassword() {
        //arrange
        when(userRepository.findByPassword(user.getPassword())).thenReturn(Optional.of(user));

        ForgotPasswordBindingModel newPassword = new ForgotPasswordBindingModel();
        newPassword.setGivenPassword(user.getPassword());
        newPassword.setPassword("123456");

        Optional<UserEntity> optUser = userRepository.findByPassword(newPassword.getGivenPassword());

        when(modelMapper.map(optUser, UserEntity.class)).thenReturn(user);
        when(passwordEncoder.encode(newPassword.getPassword())).thenReturn(newPassword.getPassword());

        //act
        serviceTest.setUserNewPassword(newPassword);

        //assert
        assertEquals(user.getPassword(), newPassword.getPassword());
    }

    @DisplayName("JUnit test verify method")
    @Test
    void testVerify_ShouldCheckForExistingVerificationCodeAndActiveUserAccount() {
        when(userRepository.findByVerificationCode(user.getVerificationCode())).thenReturn(user);

        boolean result = serviceTest.verify("123");

        assertTrue(user.isEnabled());
        assertNull(user.getVerificationCode());
        assertTrue(result);
    }

    @DisplayName("JUnit test verify method with wrong input")
    @Test
    void testVerify_ShouldCheckForExistingVerificationCodeAndReturnFalse() {
        boolean result = serviceTest.verify(null);

        assertFalse(result);
    }

    @DisplayName("JUnit test verify method with enabled user")
    @Test
    void testVerify_ShouldReturnFalseIfUserIsEnable() {
        UserEntity enabledUser = new UserEntity();

        enabledUser.setUsername("claymore");
        enabledUser.setFirstName("Galin");
        enabledUser.setLastName("Manov");
        enabledUser.setEmail("galin@gmail.com");
        enabledUser.setPassword("12345");
        enabledUser.setEnabled(true);
        enabledUser.setVerificationCode("12");

        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnum.ADMIN);

        enabledUser.setRoles(List.of(role));

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

        enabledUser.setBeehives(Set.of(beehive));

        when(userRepository.findByVerificationCode(enabledUser.getVerificationCode())).thenReturn(enabledUser);

        boolean returnFalse = serviceTest.verify(enabledUser.getVerificationCode());

        assertFalse(returnFalse);
    }

    @DisplayName("JUnit test findEmailIsPresent method")
    @Test
    void testFindEmailIsPResent_ShouldReturnTrue() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        boolean result = serviceTest.findEmailIsPresent(user.getEmail());

        assertTrue(result);
    }

    @DisplayName("JUnit test findEmailIsPresent method when we got null")
    @Test
    void testFindEmailIsPResent_ShouldReturnFalse() {
        boolean result = serviceTest.findEmailIsPresent(null);

        assertFalse(result);
    }
}
