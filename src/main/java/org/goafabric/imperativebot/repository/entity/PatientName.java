package org.goafabric.imperativebot.repository.entity;

public record PatientName(String id, String givenName, String familyName) {

    public String getFullName() {
        return String.format("%s %s", givenName, familyName);
    }
}