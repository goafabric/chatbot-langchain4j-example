package org.goafabric.imperativebot.repository.entity;

public record PatientName(String id, String givenName, String familyName) {
    @Override
    public String toString() {
        return String.format("%s %s", givenName, familyName);
    }
}