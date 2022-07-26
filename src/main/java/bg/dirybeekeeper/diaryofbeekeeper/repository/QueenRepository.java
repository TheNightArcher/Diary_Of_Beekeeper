package bg.dirybeekeeper.diaryofbeekeeper.repository;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenEntity;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QueenRepository extends JpaRepository<QueenEntity, Long> {

    Optional<QueenTypeEnum> findByQueenType(QueenTypeEnum queenType);
}
