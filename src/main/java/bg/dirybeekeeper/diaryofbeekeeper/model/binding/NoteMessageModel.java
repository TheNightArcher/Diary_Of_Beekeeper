package bg.dirybeekeeper.diaryofbeekeeper.model.binding;

public class NoteMessageModel {
    private String message;

    public NoteMessageModel() {
    }

    public NoteMessageModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
