package org.example.myagent.repository;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AddressRepository {
    public record Address(String personId, String street, String city){}

    private final List<Address> addresses;


    public AddressRepository() {
        addresses = new ArrayList<>();
        addresses.add(new Address("1", "Evergreen Terrace 753", "Springfield"));
        addresses.add(new Address("2", "EverblueTerrace 753", "Springfield"));
        addresses.add(new Address("3", "Croesus Street", "Shelbyille"));
    }

    @Tool
    public Address findByPersonId(String personId) {
        return addresses.stream()
                .filter(address -> address.personId.equals(personId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Address with id '" + personId + "' not found"));
    }

}
