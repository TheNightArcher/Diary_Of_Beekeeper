package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.binding.UserRegisterBindingModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.UserRegisterServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users/register")
    public String register() {
        return "register";
    }

    @PostMapping("/users/register")
    public String registerConfirm(@Valid UserRegisterBindingModel registerBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerBindingModel", registerBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerBindingModel"
                    , bindingResult);

            return "redirect:/users/register";
        }

        UserRegisterServiceModel user = modelMapper.map(registerBindingModel, UserRegisterServiceModel.class);
        userService.registerUser(user, getSiteURL(request));

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


    @ModelAttribute("registerBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
