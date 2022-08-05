package bg.dirybeekeeper.diaryofbeekeeper.model.binding;

import bg.dirybeekeeper.diaryofbeekeeper.model.validation.FieldMatch;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class ForgotPasswordBindingModel {

    private String email;
    private String givenPassword;
    private String password;
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenPassword() {
        return givenPassword;
    }

    public void setGivenPassword(String givenPassword) {
        this.givenPassword = givenPassword;
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
}
