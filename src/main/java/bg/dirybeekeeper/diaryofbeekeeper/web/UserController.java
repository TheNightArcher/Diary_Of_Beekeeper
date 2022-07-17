package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.binding.UserRegisterBindingModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.UserRegisterServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel registerBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerBindingModel", registerBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerBindingModel"
                    , bindingResult);

            return "redirect:/users/register";
        }

        UserRegisterServiceModel user = modelMapper.map(registerBindingModel, UserRegisterServiceModel.class);
        userService.registerUser(user);

        return "redirect:/users/login";
    }

    @ModelAttribute("registerBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }
}
