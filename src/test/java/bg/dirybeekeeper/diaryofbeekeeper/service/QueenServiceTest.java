package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenTypeEnum;
import bg.dirybeekeeper.diaryofbeekeeper.repository.QueenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueenServiceTest {

    @Mock
    private QueenRepository queenRepositoryMock;

    @InjectMocks
    private QueenService queenServiceTest;
    private QueenTypeEnum queenTypeEnum;
    private QueenEntity queen;

    @BeforeEach
    void setUp() {
        queenServiceTest = new QueenService(queenRepositoryMock);

        queen = new QueenEntity();
        queenTypeEnum = QueenTypeEnum.Yellow;
        queen.setQueenType(queenTypeEnum);
        queen.setQueenBorn(LocalDate.now());
    }

    @DisplayName("JUnit test for findQueen method")
    @Test
    public void testFindQueen_shouldReturnObject() {
        // arrange
        Optional<QueenTypeEnum> type = Optional.of(QueenTypeEnum.Yellow);
        when(queenRepositoryMock.findByQueenType(queen.getQueenType())).thenReturn(type);

        //act
        QueenTypeEnum result = queenServiceTest.findType(queenTypeEnum);

        //assert
        assertThat(result).isNotNull();
    }
}