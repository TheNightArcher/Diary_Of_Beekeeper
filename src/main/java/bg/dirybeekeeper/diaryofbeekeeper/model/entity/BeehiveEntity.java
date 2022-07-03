package bg.dirybeekeeper.diaryofbeekeeper.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "beehives")
public class BeehiveEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String CurrentNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private QueenEntity queen;

    @ManyToOne(fetch = FetchType.EAGER)
    private BeeFrameEntity frame;

    @OneToMany()
    private List<NutritionEntity> lastNutrition =new ArrayList<>();

    @Column(nullable = false)
    private byte capacity;

    private boolean isAlive;

    public String getCurrentNumber() {
        return CurrentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        CurrentNumber = currentNumber;
    }

    public QueenEntity getQueen() {
        return queen;
    }

    public void setQueen(QueenEntity queen) {
        this.queen = queen;
    }

    public BeeFrameEntity getFrame() {
        return frame;
    }

    public void setFrame(BeeFrameEntity frame) {
        this.frame = frame;
    }

    public List<NutritionEntity> getLastNutrition() {
        return lastNutrition;
    }

    public void setLastNutrition(List<NutritionEntity> lastNutrition) {
        this.lastNutrition = lastNutrition;
    }

    public byte getCapacity() {
        return capacity;
    }

    public void setCapacity(byte capacity) {
        this.capacity = capacity;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
