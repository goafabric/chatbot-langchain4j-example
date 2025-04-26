package org.goafabric.dbagentnew.dbagent.persistence.entity;

import jakarta.persistence.*;


@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Version //optimistic locking
    private Long version;

    private String street;
    private String city;

    public Address(String id, Long version, String street, String city) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.version = version;
    }

    Address() {
    }

    public String getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "AddressEo{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
