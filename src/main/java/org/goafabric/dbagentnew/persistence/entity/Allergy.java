package org.goafabric.dbagentnew.persistence.entity;

import jakarta.persistence.*;


@Entity
@Table(name="allergy")
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Version
    private Long version;

    private String allergy;

    public Allergy(String id, Long version, String allergy) {
        this.id = id;
        this.version = version;
        this.allergy = allergy;
    }

    Allergy() {
    }

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getAllergy() {
        return allergy;
    }

    @Override
    public String toString() {
        return "AllergyEo{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", allergy='" + allergy + '\'' +
                '}';
    }
}
