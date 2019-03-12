package es.carlop.petclinic.controllers;

import es.carlop.petclinic.model.Owner;
import es.carlop.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // We don't want to allow the web forms to address and manipulate
    // the ID property
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {

        model.addAttribute("owner", Owner.builder().build());

        return "owners/find";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> results = ownerService.findAllByLastNameLikeIgnoreCase("%" + owner.getLastName() + "%");

        if (results.isEmpty()) {
            result.rejectValue("lastName", "notfound", "Not found!");
            return "owners/find";
        } else if (results.size() == 1) {
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            model.addAttribute("owners", results);
            return "owners/index";
        }
    }

    @GetMapping("/{id}")
    public ModelAndView showOwner(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("owners/details");

        modelAndView.addObject(ownerService.findById(id));

        return modelAndView;
    }

}
