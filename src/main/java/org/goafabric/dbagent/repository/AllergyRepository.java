package org.goafabric.dbagent.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AllergyRepository {
    public record Allergy(String personId, String description) {}

    private final List<Allergy> allergiesEnglish;
    private final List<Allergy> allergiesGerman;

    public AllergyRepository() {
        allergiesEnglish = new ArrayList<>();
        allergiesEnglish.add(new Allergy("1", "work"));
        allergiesEnglish.add(new Allergy("2", "peanuts"));
        allergiesEnglish.add(new Allergy("3", "bees"));

        allergiesGerman = new ArrayList<>();
        allergiesGerman.add(new Allergy("1", "arbeit"));
        allergiesGerman.add(new Allergy("2", "erdnüsse"));
        allergiesGerman.add(new Allergy("3", "bienen"));
    }

    public Allergy findAllergyById(String id) {
        return allergiesEnglish.stream()
                .filter(allergy -> allergy.personId.equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Allergy with id '" + id + "' not found"));
    }

    public Allergy findByAllergy(String description, String language) {
        if (language.equals("english")) {
            return allergiesEnglish.stream()
                    .filter(allergy -> allergy.description.equals(description.toLowerCase()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Allergy with description '" + description + "' not found"));
        }
        if (language.equals("german")) {
            return allergiesGerman.stream()
                    .filter(allergy -> allergy.description.equals(description.toLowerCase()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Allergy '" + description + "' nicht gefunden"));
        }
        throw new IllegalStateException("unknown language: " + language);
    }
}
