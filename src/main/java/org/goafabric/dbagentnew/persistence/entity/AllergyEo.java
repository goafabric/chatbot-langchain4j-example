package org.goafabric.dbagentnew.persistence.entity;

import jakarta.persistence.*;


@Entity
@Table(name="allergy")
public class AllergyEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Version
    private Long version;

    private String allergy;

    public AllergyEo(String id, Long version, String allergy) {
        this.id = id;
        this.version = version;
        this.allergy = allergy;
    }

    AllergyEo() {
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
