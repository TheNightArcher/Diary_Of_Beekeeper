package bg.dirybeekeeper.diaryofbeekeeper.model.view;

public class NoteDisplayView {
    private Long id;
    private String message;

    public NoteDisplayView() {
    }

    public NoteDisplayView(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
