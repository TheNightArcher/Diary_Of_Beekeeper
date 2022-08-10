package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private UserEntity user;

    @BeforeEach
    void setUp() {


        user = new UserEntity();

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

    @Test
    @WithAnonymousUser
    void testRegistrationPageShowsUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithAnonymousUser
    void testUserRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .param("username", user.getUsername())
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("confirmPassword", user.getPassword())
                        .cookie(new Cookie("lang", Locale.ENGLISH.getLanguage()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.registerBindingModel"))
                .andReturn().getFlashMap().get("org.springframework.validation.BindingResult.registerBindingModel");
    }

    @Test
    @WithAnonymousUser
    void testLoginPageShowsUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithAnonymousUser
    void testLoginWithWrongInput_ShouldRedirectBackToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login-error")
                        .param("username", "Pato")
                        .param("password", "123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAdminPageShowsUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/admin/view")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-view"));
    }

    @Test
    void testProcessRegisterPageShowsUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/process_register")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register-success"));
    }

    @Test
    void testVerificationProcess_WithOutCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/verify")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("verify-fail"));
    }
}
