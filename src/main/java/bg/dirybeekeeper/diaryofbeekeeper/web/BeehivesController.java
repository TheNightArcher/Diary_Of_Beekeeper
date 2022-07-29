package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.binding.BeehiveAddBindingModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.BeehiveAddServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.UserBeehivesView;
import bg.dirybeekeeper.diaryofbeekeeper.service.BeehiveService;
import bg.dirybeekeeper.diaryofbeekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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

        return "redirect:/users/beehives/all";
    }

    @GetMapping("/beehives/all")
    public String myBeehives(Model model, @AuthenticationPrincipal BeekeeperUserDetails userDetails) {
        List<UserBeehivesView> myBeehives = userService.findMyBeehives(userDetails.getUsername());

        model.addAttribute("myBeehives", myBeehives);

        return "beehives";
    }

    @ModelAttribute
    public BeehiveAddBindingModel beehiveAddBindingModel() {
        return new BeehiveAddBindingModel();
    }
}
