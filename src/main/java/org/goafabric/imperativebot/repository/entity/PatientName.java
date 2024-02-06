package org.goafabric.imperativebot.repository.entity;

public record PatientName(String id, String name) {
    @Override
    public String toString() {
        return String.format("%s %s", id, name);
    }
}