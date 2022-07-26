package bg.dirybeekeeper.diaryofbeekeeper.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity(name = "queens")
public class QueenEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QueenTypeEnum queenType;

    private LocalDate queenBorn;

    public QueenTypeEnum getQueenType() {
        return queenType;
    }

    public void setQueenType(QueenTypeEnum queenType) {
        this.queenType = queenType;
    }

    public LocalDate getQueenBorn() {
        return queenBorn;
    }

    public void setQueenBorn(LocalDate queenBorn) {
        this.queenBorn = queenBorn;
    }
}
