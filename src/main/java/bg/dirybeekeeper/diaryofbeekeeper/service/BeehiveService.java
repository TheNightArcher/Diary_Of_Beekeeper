package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.BeehiveAddServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.repository.BeehiveRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BeehiveService {
    private final BeehiveRepository beehiveRepository;
    private final QueenService queenService;
    private final ModelMapper modelMapper;

    public BeehiveService(BeehiveRepository beehiveRepository, QueenService queenService, ModelMapper modelMapper) {
        this.beehiveRepository = beehiveRepository;
        this.queenService = queenService;
        this.modelMapper = modelMapper;
    }

    public BeehiveEntity addBeehive(BeehiveAddServiceModel beehiveAddServiceModel) {
        BeehiveEntity beehive = modelMapper.map(beehiveAddServiceModel, BeehiveEntity.class);

        QueenEntity queen = new QueenEntity();

        queen.setQueenType(beehiveAddServiceModel.getQueenType());
        queen.setQueenBorn(beehiveAddServiceModel.getBorn());

        beehive.setQueen(queen);
        beehive.setLastNutrition(beehiveAddServiceModel.getLastNutrition());

        return beehiveRepository.save(beehive);
    }

    public void deleteBeehiveById(Long id) {
        beehiveRepository.deleteById(id);
    }

    public BeehiveEntity findBeehive(Long id) {
        return beehiveRepository.findById(id)
                .orElse(null);
    }
}
