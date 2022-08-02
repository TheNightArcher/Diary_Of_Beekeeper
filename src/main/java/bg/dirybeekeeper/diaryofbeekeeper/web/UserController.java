package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.binding.UserRegisterBindingModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.UserRegisterServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.UsersView;
import bg.dirybeekeeper.diaryofbeekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final LocaleResolver localeResolver;

    public UserController(UserService userService, ModelMapper modelMapper, LocaleResolver localeResolver) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.localeResolver = localeResolver;
    }

    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    @PostMapping("/users/login-error")
    public String failedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("username", username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/users/register")
    public String register() {
        return "register";
    }

    @PostMapping("/users/register")
    public String registerConfirm(@Valid UserRegisterBindingModel registerBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest request) {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerBindingModel", registerBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerBindingModel"
                    , bindingResult);

            return "redirect:/users/register";
        }

        UserRegisterServiceModel user = modelMapper.map(registerBindingModel, UserRegisterServiceModel.class);
        userService.registerUser(user, localeResolver.resolveLocale(request));

        return "redirect:/users/process_register";
    }

    @GetMapping("/users/process_register")
    public String processRegister() {
        return "register-success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify-success";
        } else {
            return "verify-fail";
        }
    }

    @GetMapping("/users/admin/view")
    private String adminView(Model model,
                             @PageableDefault(
                                     sort = "id",
                                     direction = Sort.Direction.ASC,
                                     size = 4
                             ) Pageable pageable) {
        Page<UsersView> users = userService.findAllUsers(pageable);

        model.addAttribute("users", users);

        return "admin-view";
    }

    @ModelAttribute("registerBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }
}
