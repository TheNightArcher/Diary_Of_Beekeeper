package bg.dirybeekeeper.diaryofbeekeeper.model.view;

import bg.dirybeekeeper.diaryofbeekeeper.model.entity.BeehiveEntity;

import java.util.Set;

public class UsersView {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isEnabled;
    private Set<BeehiveEntity> beehives;

    public UsersView() {
    }

    public UsersView(Long id,
                     String username,
                     String firstName,
                     String lastName,
                     String email,
                     boolean isEnabled,
                     Set<BeehiveEntity> beehives) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isEnabled = isEnabled;
        this.beehives = beehives;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<BeehiveEntity> getBeehives() {
        return beehives;
    }

    public void setBeehives(Set<BeehiveEntity> beehives) {
        this.beehives = beehives;
    }
}
