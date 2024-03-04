package org.goafabric.imperativebot.repository.entity;

public record PatientName(String id, String givenName, String givenSoundex, String familyName, String familySoundex) {

    public String getFullName() {
        return String.format("%s %s", givenName, familyName);
    }
}