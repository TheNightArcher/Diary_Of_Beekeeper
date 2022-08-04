package bg.dirybeekeeper.diaryofbeekeeper.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "beehives")
public class BeehiveEntity extends BaseEntity {

    @Column(nullable = false)
    private Integer currentNumber;

    @ManyToOne(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private QueenEntity queen;

    private float length;
    private float high;
    private float width;
    private LocalDate lastNutrition;
    private byte capacity;

    @Enumerated(value = EnumType.STRING)
    private BeehiveStatusEnum status;

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public LocalDate getLastNutrition() {
        return lastNutrition;
    }

    public void setLastNutrition(LocalDate lastNutrition) {
        this.lastNutrition = lastNutrition;
    }

    public byte getCapacity() {
        return capacity;
    }

    public void setCapacity(byte capacity) {
        this.capacity = capacity;
    }

    public BeehiveStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BeehiveStatusEnum status) {
        this.status = status;
    }

    public QueenEntity getQueen() {
        return queen;
    }

    public void setQueen(QueenEntity queen) {
        this.queen = queen;
    }
}
