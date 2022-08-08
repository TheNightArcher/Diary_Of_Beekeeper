package bg.dirybeekeeper.diaryofbeekeeper.repository;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeehiveRepository extends JpaRepository<BeehiveEntity, Long> {
    Optional<BeehiveEntity> findByCurrentNumber(Integer number);
}
