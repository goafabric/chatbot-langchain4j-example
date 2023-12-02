package org.goafabric.agent.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AllergyRepository {
    public record Allergy(String personId, String description) {}

    private final List<Allergy> allergies;

    public AllergyRepository() {
        allergies = new ArrayList<>();
        allergies.add(new Allergy("1", "work"));
        allergies.add(new Allergy("2", "peanuts"));
        allergies.add(new Allergy("3", "bees"));
    }

    public Allergy findAllergyById(String id) {
        return allergies.stream()
                .filter(allergy -> allergy.personId.equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Allergy with id '" + id + "' not found"));
    }

    public Allergy findByAllergy(String description) {
        return allergies.stream()
                .filter(allergy -> allergy.description.equals(description.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Allergy with description '" + description + "' not found"));
    }
}
