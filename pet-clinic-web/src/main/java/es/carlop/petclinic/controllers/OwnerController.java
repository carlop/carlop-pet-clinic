package es.carlop.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    @RequestMapping({"/owners", "/owners.html", "/index", "/owners/index.html"})
    public String index() {
        return "owners/index";
    }

}
