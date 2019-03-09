package es.carlop.petclinic.bootstrap;

import es.carlop.petclinic.model.*;
import es.carlop.petclinic.services.OwnerService;
import es.carlop.petclinic.services.PetTypeService;
import es.carlop.petclinic.services.SpecialityService;
import es.carlop.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService,PetTypeService petTypeService,
                      SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }

    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Rachel");
        owner1.setLastName("Digs");
        owner1.setAddress("125 Random Street");
        owner1.setCity("Grenade");
        owner1.setTelephone("123123126");

        Pet rachelsDog = new Pet();
        rachelsDog.setName("Coco");
        rachelsDog.setOwner(owner1);
        rachelsDog.setBirthDate(LocalDate.now());
        rachelsDog.setPetType(savedDogPetType);
        owner1.getPets().add(rachelsDog);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Michael");
        owner2.setLastName("Weston");
        owner2.setAddress("123 Fake Street");
        owner2.setCity("Miami");
        owner2.setTelephone("123123123");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setOwner(owner2);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Rosco");
        owner2.getPets().add(mikesPet);

        ownerService.save(owner2);

        Owner owner3 = new Owner();
        owner3.setFirstName("Fiona");
        owner3.setLastName("Glenanne");
        owner3.setAddress("125 Fake Lane");
        owner3.setCity("Springfield");
        owner3.setTelephone("123123126");

        Pet fionasCat = new Pet();
        fionasCat.setName("Just Cat");
        fionasCat.setOwner(owner3);
        fionasCat.setBirthDate(LocalDate.now());
        fionasCat.setPetType(savedCatPetType);
        owner3.getPets().add(fionasCat);

        ownerService.save(owner3);

        System.out.println("Loaded Owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Ben");
        vet2.setLastName("Linus");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }

}
