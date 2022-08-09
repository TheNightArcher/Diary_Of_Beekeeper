package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveStatusEnum;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenTypeEnum;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.BeehiveAddServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.EditBeehiveServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.repository.BeehiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeehiveServiceTest {

    @Mock
    private BeehiveRepository mockBeehiveRepository;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private BeehiveService beehiveServiceTest;

    private BeehiveEntity beehive;
    private BeehiveAddServiceModel beehiveAddServiceModel;
    private EditBeehiveServiceModel editBeehiveServiceModel;

    @BeforeEach
    void setUp() {
        beehiveServiceTest = new BeehiveService(mockBeehiveRepository, modelMapperMock);


        //Set up beehiveEntity
        beehive = new BeehiveEntity();
        beehive.setId(1L);
        beehive.setCurrentNumber(1);

        QueenEntity queen = new QueenEntity();

        queen.setQueenType(QueenTypeEnum.Yellow);
        queen.setQueenBorn(LocalDate.now());

        beehive.setQueen(queen);
        beehive.setLength(10);
        beehive.setHigh(10);
        beehive.setWidth(10);
        beehive.setLastNutrition(LocalDate.now());
        beehive.setCapacity((byte) 12);
        beehive.setStatus(BeehiveStatusEnum.WORKING);


        //Setup Add method
        beehiveAddServiceModel = new BeehiveAddServiceModel();

        beehiveAddServiceModel.setCurrentNumber(1);
        beehiveAddServiceModel.setQueenType(QueenTypeEnum.Yellow);
        beehiveAddServiceModel.setBorn(LocalDate.now());
        beehiveAddServiceModel.setLength(10);
        beehiveAddServiceModel.setHigh(10);
        beehiveAddServiceModel.setWidth(10);
        beehiveAddServiceModel.setLastNutrition(LocalDate.now());
        beehiveAddServiceModel.setCapacity((byte) 12);

        editBeehiveServiceModel = new EditBeehiveServiceModel();

        editBeehiveServiceModel.setQueenType(QueenTypeEnum.Yellow);
        editBeehiveServiceModel.setBorn(LocalDate.now());
        editBeehiveServiceModel.setLength(12);
        editBeehiveServiceModel.setHigh(12);
        editBeehiveServiceModel.setWidth(12);
        editBeehiveServiceModel.setLastNutrition(LocalDate.now());
        editBeehiveServiceModel.setCapacity((byte) 10);
    }


    @DisplayName("JUnit test for addBeehive method")
    @Test
    void testAddBeehive_ReturnObject() {
        // arrange
        when(modelMapperMock.map(beehiveAddServiceModel, BeehiveEntity.class)).thenReturn(beehive);

        when(mockBeehiveRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //act
        BeehiveEntity result = beehiveServiceTest.addBeehive(beehiveAddServiceModel);

        //assert
        verify(mockBeehiveRepository).save(any());
        assertEquals(beehive.getCurrentNumber(), result.getCurrentNumber());
        assertEquals(beehive.getCapacity(), result.getCapacity());
    }

    @DisplayName("JUnit test for editBeehive method")
    @Test
    void testEditBeehive_ReturnNewEditedObject() {

        // arrange
        when(mockBeehiveRepository.findById(any())).thenReturn(Optional.of(beehive));

        mockBeehiveRepository.save(any());

        Optional<BeehiveEntity> opt = mockBeehiveRepository.findById(1L);

        when(modelMapperMock.map(opt, BeehiveEntity.class)).thenReturn(beehive);

        given(mockBeehiveRepository.save(beehive))
                .willReturn(beehive);

        //act
        BeehiveEntity result = beehiveServiceTest.editBeehive(beehive.getId(), editBeehiveServiceModel);

        //assert
        verify(mockBeehiveRepository).save(result);
        assertEquals(beehive.getHigh(), result.getHigh());
        assertEquals(beehive.getCapacity(), result.getCapacity());
    }

    @DisplayName("JUnit test for deleteBeehive method")
    @Test
    void testDeleteBeehive() {
        // arrange
        long beehiveId = 1;
        willDoNothing().given(mockBeehiveRepository).deleteById(beehiveId);

        //act
        beehiveServiceTest.deleteBeehiveById(beehiveId);

        //assert
        verify(mockBeehiveRepository, times(1)).deleteById(beehiveId);
    }

    @DisplayName("JUnit test for findBeehive metgod")
    @Test
    void testFindBeehive_shouldReturnObject() {

        when(mockBeehiveRepository.findById(beehive.getId())).thenReturn(Optional.of(beehive));

        BeehiveEntity result = beehiveServiceTest.findBeehive(this.beehive.getId());

        assertThat(result).isNotNull();
    }
}
