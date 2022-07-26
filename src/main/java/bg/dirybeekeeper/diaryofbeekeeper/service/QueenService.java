package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenTypeEnum;
import bg.dirybeekeeper.diaryofbeekeeper.repository.QueenRepository;
import org.springframework.stereotype.Service;

@Service
public class QueenService {

    private final QueenRepository queenRepository;

    public QueenService(QueenRepository queenRepository) {
        this.queenRepository = queenRepository;
    }


    public QueenTypeEnum findType(QueenTypeEnum queenType) {
        return queenRepository.findByQueenType(queenType)
                .orElse(null);
    }
}
