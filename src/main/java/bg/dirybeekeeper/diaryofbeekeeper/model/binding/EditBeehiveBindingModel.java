package bg.dirybeekeeper.diaryofbeekeeper.model.binding;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveStatusEnum;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenTypeEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class EditBeehiveBindingModel {

    private Long id;

    private QueenTypeEnum queenType;

    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate queenBorn;

    @Positive
    private float length;

    @Positive
    private float high;

    @Positive
    private float width;

    @Positive
    private byte capacity;

    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lastNutrition;

    @NotNull
    private BeehiveStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public byte getCapacity() {
        return capacity;
    }

    public void setCapacity(byte capacity) {
        this.capacity = capacity;
    }

    public LocalDate getLastNutrition() {
        return lastNutrition;
    }

    public void setLastNutrition(LocalDate lastNutrition) {
        this.lastNutrition = lastNutrition;
    }

    public BeehiveStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BeehiveStatusEnum status) {
        this.status = status;
    }
}
