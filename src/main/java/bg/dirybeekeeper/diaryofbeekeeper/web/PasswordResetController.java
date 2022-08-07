package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.binding.ForgotPasswordBindingModel;
import bg.dirybeekeeper.diaryofbeekeeper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class PasswordResetController {

    private final UserService userService;
    private final LocaleResolver localeResolver;

    public PasswordResetController(UserService userService, LocaleResolver localeResolver) {
        this.userService = userService;
        this.localeResolver = localeResolver;
    }

    @GetMapping("/users/forgot")
    public String forgotPassword() {
        return "send-email";
    }

    @PostMapping("/users/forgot")
    public String forgotPasswordEmailConfirm(ForgotPasswordBindingModel forgotPasswordBindingModel,
                                             HttpServletRequest request) {

        if (!userService.findEmailIsPresent(forgotPasswordBindingModel.getEmail())) {
            return "redirect:/";
        }

        userService.sendToUserVerificationCode(forgotPasswordBindingModel.getEmail(), localeResolver.resolveLocale(request));

        return "redirect:/";
    }

    @GetMapping("/change")
    public String changePassword() {
        return "set-password";
    }

    @PostMapping("/change")
    public String changePasswordConfirm(@Valid ForgotPasswordBindingModel forgotPasswordBindingModel,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerBindingModel", forgotPasswordBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.forgotPasswordBindingModel"
                    , bindingResult);

            return "redirect:/change";
        }

        userService.setUserNewPassword(forgotPasswordBindingModel);

        return "redirect:/users/login";
    }

    @ModelAttribute
    public ForgotPasswordBindingModel forgotPasswordBindingModel() {
        return new ForgotPasswordBindingModel();
    }
}
