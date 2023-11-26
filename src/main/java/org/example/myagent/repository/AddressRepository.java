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
        addresses.add(new Address("3", "Croesus Street", "Shelbyville"));
    }

    @Tool
    public Address findById(String id) {
        return addresses.stream()
                .filter(address -> address.personId.equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Address with id '" + id + "' not found"));
    }


    //@Tool
    public Address findByCity(String city) {
        return addresses.stream()
                .filter(address -> address.city.toLowerCase().equals(city.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Address with city '" + city + "' not found"));
    }
}
