package bg.dirybeekeeper.diaryofbeekeeper.model.view;

public class UserBeehivesView {

    private Long id;
    private Integer currentNumber;
    private byte capacity;

    public UserBeehivesView(Long id, Integer currentNumber, byte capacity) {
        this.id = id;
        this.currentNumber = currentNumber;
        this.capacity = capacity;
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

    public byte getCapacity() {
        return capacity;
    }

    public void setCapacity(byte capacity) {
        this.capacity = capacity;
    }
}
