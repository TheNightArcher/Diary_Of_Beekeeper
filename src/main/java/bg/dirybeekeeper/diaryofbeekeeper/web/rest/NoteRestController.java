package bg.dirybeekeeper.diaryofbeekeeper.web.rest;

import bg.dirybeekeeper.diaryofbeekeeper.exception.ObjectNotFoundException;
import bg.dirybeekeeper.diaryofbeekeeper.model.binding.NoteMessageModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.binding.CreateNoteModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.NoteDisplayView;
import bg.dirybeekeeper.diaryofbeekeeper.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class NoteRestController {
    private final NoteService noteService;

    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{beehiveId}/comments")
    public ResponseEntity<List<NoteDisplayView>> getComments(@PathVariable("beehiveId") Long routeId) {
        return ResponseEntity.ok(noteService.getAllCommentsForRoute(routeId));
    }

    @PostMapping(value = "/{beehiveId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<NoteDisplayView> createComment(@PathVariable Long beehiveId,
                                                         @AuthenticationPrincipal BeekeeperUserDetails userDetails,
                                                         @RequestBody NoteMessageModel noteMessageModel) {

        CreateNoteModel commentModel = new CreateNoteModel();
        commentModel.setBeehiveId(beehiveId);
        commentModel.setUsername(userDetails.getUsername());
        commentModel.setMessage(noteMessageModel.getMessage());

        NoteDisplayView comment = noteService.createComment(commentModel);

        return ResponseEntity
                .created(URI.create(String.format("/api/%d/comments/%d", beehiveId, comment.getId())))
                .body(comment);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<ErrorApiResponse> handleRouteNotFound() {
        return ResponseEntity.status(404).body(new ErrorApiResponse("Such route doesn't exist!", 1004));
    }
}

class ErrorApiResponse {
    private String message;
    private Integer errorCode;

    public ErrorApiResponse(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public ErrorApiResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}