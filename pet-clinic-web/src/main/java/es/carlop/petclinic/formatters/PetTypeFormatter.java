package es.carlop.petclinic.formatters;

import es.carlop.petclinic.model.PetType;
import es.carlop.petclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Collection<PetType> findPetTypes = petTypeService.findAll();
        for (PetType petType : findPetTypes) {
            if (petType.getName().equals(text)) {
                return petType;
            }
        }
        throw new ParseException("PetType not found: " + text, 0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
