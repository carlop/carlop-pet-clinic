package es.carlop.petclinic.services.map;

import es.carlop.petclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetMapServiceTest {

    private PetMapService petMapService;

    private Long petId = 1L;

    @BeforeEach
    void setUp() {
        petMapService = new PetMapService();

        petMapService.save(Pet.builder().id(petId).build());
    }

    @Test
    void findAll() {
        Set<Pet> petSet = petMapService.findAll();
        assertEquals(1, petSet.size());
    }

    @Test
    void findByExistingId() {
        Pet pet = petMapService.findById(petId);
        assertEquals(petId, pet.getId());
    }

    @Test
    void findByNonExistingId() {
        Pet pet = petMapService.findById(2L);
        assertNull(pet);
    }

    @Test
    void findByNullId() {
        Pet pet = petMapService.findById(null);
        assertNull(pet);
    }

    @Test
    void save() {
        Long petId2 = 2L;
        Pet pet = Pet.builder().id(petId2).build();

        Pet savedPet = petMapService.save(pet);

        assertEquals(pet.getId(), savedPet.getId());

    }

    @Test
    void saveDuplicatedId() {
        Long petId2 = 1L;
        Pet pet = Pet.builder().id(petId2).build();

        Pet savedPet = petMapService.save(pet);

        assertEquals(pet.getId(), savedPet.getId());
        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void saveNoId() {
        Pet savedPet = petMapService.save(Pet.builder().build());

        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
        assertEquals(2, petMapService.findAll().size());
    }

    @Test
    void delete() {
        Pet pet = petMapService.findById(petId);
        petMapService.delete(pet);

        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void deleteWithCorrectId() {
        petMapService.deleteById(petId);

        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void deleteWithWrongId() {
        Long petId2 = 2L;
        Pet pet = Pet.builder().id(petId2).build();

        petMapService.deleteById(pet.getId());

        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteByNullId() {
        petMapService.deleteById(null);

        assertEquals(1, petMapService.findAll().size());
    }
}