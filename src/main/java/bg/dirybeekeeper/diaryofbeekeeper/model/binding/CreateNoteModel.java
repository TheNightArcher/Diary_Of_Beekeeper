package bg.dirybeekeeper.diaryofbeekeeper.model.binding;

public class CreateNoteModel {
    private long beehiveId;
    private String message;
    private String username;

    public long getBeehiveId() {
        return beehiveId;
    }

    public void setBeehiveId(long beehiveId) {
        this.beehiveId = beehiveId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
