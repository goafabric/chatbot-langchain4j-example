package org.goafabric.dbagent.ai;

import dev.langchain4j.agent.tool.Tool;
import org.goafabric.dbagent.repository.AddressRepository;
import org.goafabric.dbagent.repository.AllergyRepository;
import org.goafabric.dbagent.repository.PersonRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseTool {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final AllergyRepository allergyRepository;

    public DatabaseTool(PersonRepository personRepository, AddressRepository addressRepository, AllergyRepository allergyRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.allergyRepository = allergyRepository;
    }

    @Tool
    public PersonRepository.Person findByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    @Tool
    public PersonRepository.Person findByLastName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    @Tool
    public PersonRepository.Person findByCity(String city) {
        String personId = addressRepository.findByCity(city).personId();
        return personRepository.findPersonById(personId);
    }

    @Tool
    public PersonRepository.Person findByAllergy(String allergy, String language) {
        String personId = allergyRepository.findByAllergy(allergy, language).personId();
        return personRepository.findPersonById(personId);
    }

    //it is important to have a unique name here! if only findById, the model starts mixing things up
    @Tool
    public AddressRepository.Address findAddressById(String personId) {
        return addressRepository.findAddressById(personId);
    }

    @Tool
    public AllergyRepository.Allergy findAllergyById(String personId) {
        return allergyRepository.findAllergyById(personId);
    }
}
