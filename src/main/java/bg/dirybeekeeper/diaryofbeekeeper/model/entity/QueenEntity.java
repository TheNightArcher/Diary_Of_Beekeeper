package bg.dirybeekeeper.diaryofbeekeeper.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity(name = "queens")
public class QueenEntity extends BaseEntity {

    @Column(nullable = false)
    private QueenTypeEnum type;

    private LocalDate born;

    public QueenTypeEnum getType() {
        return type;
    }

    public void setType(QueenTypeEnum type) {
        this.type = type;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }
}
