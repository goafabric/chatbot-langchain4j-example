package org.goafabric.dbagentnew.dbagent.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Version //optimistic locking
    private Long version;

    private String firstName;

    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private List<Address> address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private List<Allergy> allergy;


    public Person(String id, Long version, String firstName, String lastName, List<Address> address, List<Allergy> allergy) {
        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.allergy = allergy;
    }

    Person() {}

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Address> getAddress() {
        return address;
    }

    public List<Allergy> getAllergy() {
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
