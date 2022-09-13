package bg.dirybeekeeper.diaryofbeekeeper.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.binding.CreateNoteModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.NoteEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.NoteDisplayView;
import bg.dirybeekeeper.diaryofbeekeeper.repository.BeehiveRepository;
import bg.dirybeekeeper.diaryofbeekeeper.repository.NoteRepository;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final BeehiveRepository beehiveRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public NoteService(NoteRepository noteRepository, BeehiveRepository beehiveRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.beehiveRepository = beehiveRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<NoteDisplayView> getAllCommentsForRoute(Long routeId) {
        BeehiveEntity beehive = beehiveRepository.findById(routeId).orElseThrow(null);

        List<NoteEntity> notes = noteRepository.findAllByBeehive(beehive).get();
        return notes.stream().map(note -> new NoteDisplayView(note.getId(),
                note.getUserComment())).collect(Collectors.toList());
    }

    public NoteDisplayView createComment(CreateNoteModel commentModel) {
        Optional<BeehiveEntity> optionalBeehiveEntity = beehiveRepository.findById(commentModel.getBeehiveId());
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(commentModel.getUsername());

        BeehiveEntity beehive = modelMapper.map(optionalBeehiveEntity, BeehiveEntity.class);
        UserEntity user = modelMapper.map(optionalUserEntity, UserEntity.class);

        NoteEntity comment = new NoteEntity();

        comment.setUserComment(commentModel.getMessage());
        comment.setBeehive(beehive);
        comment.setUser(user);

        noteRepository.save(comment);

        return new NoteDisplayView(comment.getId(), comment.getUserComment());
    }
}
