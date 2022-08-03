package bg.dirybeekeeper.diaryofbeekeeper.web;

import bg.dirybeekeeper.diaryofbeekeeper.model.binding.BeehiveAddBindingModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.service.BeehiveAddServiceModel;
import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.UserBeehiveDetailsView;
import bg.dirybeekeeper.diaryofbeekeeper.model.view.UserBeehivesView;
import bg.dirybeekeeper.diaryofbeekeeper.service.BeehiveService;
import bg.dirybeekeeper.diaryofbeekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

        return "redirect:/users/beehives/all";
    }

    @GetMapping("/beehives/all")
    public String myBeehives(@AuthenticationPrincipal BeekeeperUserDetails userDetails,
                             Model model,
                             @PageableDefault(
                                     sort = "currentNumber",
                                     direction = Sort.Direction.ASC,
                                     size = 4
                             ) Pageable pageable) {
        Page<UserBeehivesView> myBeehives = userService.findMyBeehives(userDetails.getUsername(), pageable);

        model.addAttribute("myBeehives", myBeehives);

        return "beehives";
    }

    @GetMapping("/beehives/details/{id}")
    public String beehiveDetails(@PathVariable Long id,
                                 Model model,
                                 @AuthenticationPrincipal BeekeeperUserDetails userDetails) {

        UserBeehiveDetailsView currentBeehive = userService.findBeehiveDetails(userDetails.getUsername(), id);
        model.addAttribute("currentBeehive", currentBeehive);

        return "details";
    }

    @DeleteMapping("/beehives/details/{id}")
    public String deleteBeehive(@PathVariable Long id, @AuthenticationPrincipal BeekeeperUserDetails userDetails) {
        userService.deleteBeehiveById(userDetails.getUsername(), id);
        beehiveService.deleteBeehiveById(id);
        return "redirect:/users/beehives/all";
    }

    @ModelAttribute
    public BeehiveAddBindingModel beehiveAddBindingModel() {
        return new BeehiveAddBindingModel();
    }
}
