package bg.dirybeekeeper.diaryofbeekeeper.model.service;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveStatusEnum;
import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenTypeEnum;

import java.time.LocalDate;

public class EditBeehiveService {
    private QueenTypeEnum queenType;
    private LocalDate born;
    private float length;
    private float high;
    private float width;
    private byte capacity;
    private LocalDate lastNutrition;
    private BeehiveStatusEnum status;

    public QueenTypeEnum getQueenType() {
        return queenType;
    }

    public void setQueenType(QueenTypeEnum queenType) {
        this.queenType = queenType;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
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
