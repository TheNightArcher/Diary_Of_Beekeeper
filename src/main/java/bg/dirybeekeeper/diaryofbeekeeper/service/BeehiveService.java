package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveStatusEnum;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.BeehiveAddServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.EditBeehiveService;
import bg.dirybeekeeper.diaryofbeekeeper.repository.BeehiveRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeehiveService {
    private final BeehiveRepository beehiveRepository;
    private final ModelMapper modelMapper;

    public BeehiveService(BeehiveRepository beehiveRepository, ModelMapper modelMapper) {
        this.beehiveRepository = beehiveRepository;
        this.modelMapper = modelMapper;
    }

    public BeehiveEntity addBeehive(BeehiveAddServiceModel beehiveAddServiceModel) {
        BeehiveEntity beehive = modelMapper.map(beehiveAddServiceModel, BeehiveEntity.class);

        QueenEntity queen = new QueenEntity();

        queen.setQueenType(beehiveAddServiceModel.getQueenType());
        queen.setQueenBorn(beehiveAddServiceModel.getBorn());

        beehive.setStatus(BeehiveStatusEnum.WORKING);
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

    public BeehiveEntity editBeehive(Long id, EditBeehiveService editedBeehive) {
        Optional<BeehiveEntity> beehiveFromRepo = beehiveRepository.findById(id);

        BeehiveEntity beehive = modelMapper.map(beehiveFromRepo, BeehiveEntity.class);

        beehive.getQueen().setQueenType(editedBeehive.getQueenType());
        beehive.getQueen().setQueenBorn(editedBeehive.getBorn());
        beehive.setLength(editedBeehive.getLength());
        beehive.setHigh(editedBeehive.getHigh());
        beehive.setWidth(editedBeehive.getWidth());
        beehive.setLastNutrition(editedBeehive.getLastNutrition());
        beehive.setCapacity(editedBeehive.getCapacity());
        beehive.setStatus(editedBeehive.getStatus());

        return beehive;
    }
}
