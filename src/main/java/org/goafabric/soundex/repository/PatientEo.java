package org.goafabric.soundex.repository;

import jakarta.persistence.*;

@Entity
@Table(name = "patient")
public class PatientEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String givenName;
    private String familyName;
    private String familySoundex;

    private PatientEo() {}
    public PatientEo(String id, String givenName, String familyName, String familySoundex) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.familySoundex = familySoundex;
    }

    public String getId() {
        return id;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getFamilySoundex() {
        return familySoundex;
    }


    @Override
    public String toString() {
        return "PatientEo{" +
                "id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", familySoundex='" + familySoundex + '\'' +
                '}';
    }
}
