package bg.dirybeekeeper.diaryofbeekeeper.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/users")
public class AddBeehives {

    @GetMapping("/add-beehives")
    public String addBeehives(){
        return "add-beehives";
    }
}
