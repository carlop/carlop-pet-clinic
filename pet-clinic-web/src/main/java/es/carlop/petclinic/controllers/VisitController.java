package es.carlop.petclinic.controllers;

import es.carlop.petclinic.model.Pet;
import es.carlop.petclinic.model.Visit;
import es.carlop.petclinic.services.PetService;
import es.carlop.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private static final String PETS_VISIT_FORM = "pets/visitForm";

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    // See OwnerController.java for more information about @InitBinder
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Map<String, Object> model) {
        Pet pet = petService.findById(petId);
        model.put("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/visits/new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Map<String, Object> model) {
        return PETS_VISIT_FORM;
    }

    @PostMapping("/visits/new")
    public String processNewVisitForm(@PathVariable("ownerId") Long ownerId, @Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return PETS_VISIT_FORM;
        } else {
            visitService.save(visit);
            return "redirect:/owners/" + ownerId;
        }
    }

    @GetMapping("/visits/{visitId}/edit")
    public String initUpdateVisitForm(@PathVariable("visitId") Long visitId, Map<String, Object> model) {
        model.put("visit", visitService.findById(visitId));
        return PETS_VISIT_FORM;
    }

    @PostMapping("/visits/{visitId}/edit")
    public String processUpdateVisitForm(@PathVariable("ownerId") Long ownerId, @Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return PETS_VISIT_FORM;
        } else {
            visitService.save(visit);
            return "redirect:/owners/" + ownerId;
        }
    }

}
