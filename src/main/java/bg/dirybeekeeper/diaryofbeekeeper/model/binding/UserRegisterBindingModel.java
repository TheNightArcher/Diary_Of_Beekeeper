package bg.dirybeekeeper.diaryofbeekeeper.model.binding;

import bg.dirybeekeeper.diaryofbeekeeper.model.validation.FieldMatch;
import bg.dirybeekeeper.diaryofbeekeeper.model.validation.UniqueUserEmail;
import bg.dirybeekeeper.diaryofbeekeeper.model.validation.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class UserRegisterBindingModel {

    @NotBlank
    @Size(min = 3, max = 25)
    @NotEmpty
    @UniqueUsername(message = "The username should be unique.")
    private String username;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank
    @NotEmpty()
    @Email()
    @UniqueUserEmail()
    private String email;

    @NotBlank
    @Size(min = 5)
    private String password;

    @NotBlank
    @Size(min = 5)
    private String confirmPassword;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegisterBindingModel{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
