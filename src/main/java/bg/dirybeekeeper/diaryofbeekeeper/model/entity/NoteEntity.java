package bg.dirybeekeeper.diaryofbeekeeper.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "comments")
public class NoteEntity extends BaseEntity {

    private String userComment;

    @ManyToOne
    private BeehiveEntity beehive;

    @ManyToOne
    private UserEntity user;

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public BeehiveEntity getBeehive() {
        return beehive;
    }

    public void setBeehive(BeehiveEntity beehive) {
        this.beehive = beehive;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
