package bg.dirybeekeeper.diaryofbeekeeper.model.entity;

import javax.persistence.Entity;

@Entity(name = "frames")
public class BeeFrameEntity extends BaseEntity {

    private float length;
    private float high;
    private float width;

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
}
