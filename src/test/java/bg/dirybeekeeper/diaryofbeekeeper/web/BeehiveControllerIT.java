package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.*;
import bg.dirybeekeeper.diaryofbeekeeper.repository.BeehiveRepository;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import bg.dirybeekeeper.diaryofbeekeeper.utils.UtilDataTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ContextConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BeehiveControllerIT {

    private final static String LENGTH = "1";
    private final static String HIGH = "1";
    private final static String WIDTH = "1";
    private final static String CAPACITY = "12";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BeehiveRepository beehiveRepository;
    @Autowired
    private UtilDataTest utilDataTest;

    private UserEntity user;
    private BeehiveEntity beehive;

    @BeforeEach
    void setUp() {
        utilDataTest = new UtilDataTest(userRepository, beehiveRepository);
        user = utilDataTest.createUser();
        beehive = utilDataTest.createBeehive();
    }

    @AfterEach
    void TearDown(){
        utilDataTest.clearDatabase();
    }

    @Test
    @WithMockUser
    void testAddBeehivePageShowsUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/add-beehives")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    void testAddBeehive() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/add-beehives")
                        .param("currentNumber", beehive.getCurrentNumber().toString())
                        .param("queenType", beehive.getQueen().getQueenType().toString())
                        .param("queenBorn", beehive.getQueen().getQueenBorn().toString())
                        .param("length", LENGTH)
                        .param("high", HIGH)
                        .param("width", WIDTH)
                        .param("capacity", CAPACITY)
                        .param("lastNutrition", beehive.getLastNutrition().toString())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/beehives/all"));
    }
}
