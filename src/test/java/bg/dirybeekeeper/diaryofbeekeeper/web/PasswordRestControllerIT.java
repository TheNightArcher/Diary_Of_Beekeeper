package bg.dirybeekeeper.diaryofbeekeeper.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PasswordRestControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithAnonymousUser
    void testForgotPasswordPageShows() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/forgot")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testForgotPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/forgot")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    void testChanePasswordPageShows() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/change")
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
