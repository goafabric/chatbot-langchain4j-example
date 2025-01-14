package org.goafabric.dbagentnew.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "person")
public class PersonEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Version //optimistic locking
    private Long version;

    private String firstName;

    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private List<AddressEo> address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private List<AllergyEo> allergy;


    public PersonEo(String id, Long version, String firstName, String lastName, List<AddressEo> address, List<AllergyEo> allergy) {
        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.allergy = allergy;
    }

    PersonEo() {}

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<AddressEo> getAddress() {
        return address;
    }

    public List<AllergyEo> getAllergy() {
        return allergy;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "PersonEo{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", allergy=" + allergy +
                '}';
    }
}
