package org.example.myagent.repository;

import dev.langchain4j.agent.tool.Tool;
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
        allergies.add(new Allergy("1", "hard work"));
        allergies.add(new Allergy("2", "peanuts"));
        allergies.add(new Allergy("3", "bees"));
    }

    @Tool //it is important to have a unique name here! if only findById, the model starts mixing things up
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
