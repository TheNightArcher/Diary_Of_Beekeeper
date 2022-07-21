package bg.dirybeekeeper.diaryofbeekeeper.repository;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM users u WHERE u.verificationCode = ?1")
    public UserEntity findByVerificationCode(String code);
}
