package bg.dirybeekeeper.diaryofbeekeeper.model.view;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.QueenTypeEnum;

import java.time.LocalDate;

public class UserBeehiveDetailsView {

    private Long id;
    private Integer currentNumber;
    private QueenTypeEnum queenType;
    private LocalDate queenBorn;
    private float length;
    private float high;
    private float width;
    private LocalDate lastNutrition;
    private byte capacity;
    private boolean isAlive;

    public UserBeehiveDetailsView(Long id,
                                  Integer currentNumber,
                                  QueenTypeEnum queenType,
                                  LocalDate queenBorn,
                                  float length,
                                  float high,
                                  float width,
                                  LocalDate lastNutrition,
                                  byte capacity,
                                  boolean isAlive) {
        this.id = id;
        this.currentNumber = currentNumber;
        this.queenType = queenType;
        this.queenBorn = queenBorn;
        this.length = length;
        this.high = high;
        this.width = width;
        this.lastNutrition = lastNutrition;
        this.capacity = capacity;
        this.isAlive = isAlive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
