package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.binding.BeehiveAddBindingModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.BeehiveAddServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.service.BeehiveService;
import bg.dirybeekeeper.diaryofbeekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller()
@RequestMapping("/users")
public class BeehivesController {

    private final BeehiveService beehiveService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public BeehivesController(BeehiveService beehiveService, UserService userService, ModelMapper modelMapper) {
        this.beehiveService = beehiveService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add-beehives")
    public String addBeehives() {
        return "add-beehives";
    }

    @PostMapping("/add-beehives")
    public String addBeehiveConfirm(@Valid BeehiveAddBindingModel beehiveAddBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal BeekeeperUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("beehiveAddBindingModel", beehiveAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beehiveAddBindingModel"
                    , bindingResult);

            return "redirect:/users/add-beehives";
        }

        BeehiveAddServiceModel beehive = modelMapper.map(beehiveAddBindingModel, BeehiveAddServiceModel.class);
        userService.addBeehive(beehiveService.addBeehive(beehive), userDetails);

        return "redirect:/";
    }

    @ModelAttribute
    public BeehiveAddBindingModel beehiveAddBindingModel() {
        return new BeehiveAddBindingModel();
    }
}
