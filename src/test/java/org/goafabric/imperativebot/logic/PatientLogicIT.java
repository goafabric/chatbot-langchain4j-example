package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.PatientLogic;
import org.goafabric.imperativebot.repository.entity.PatientEo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientLogicIT {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatientLogic patientLogic;

    @Test
    public void test() {
        assertThat(findByFamilyNameAndGivenName("Meyers", "")).isNotEmpty();
        assertThat(findByFamilyNameAndGivenName("Meiers", "")).isNotEmpty();

        assertThat(findByFamilyNameAndGivenName("Meyers", "Michael")).isNotEmpty();
        assertThat(findByFamilyNameAndGivenName("Meiers", "Michael")).isNotEmpty();

        assertThat(findByFamilyNameAndGivenName("Mey", "Mic")).isNotEmpty();

        assertThat(findByFamilyNameAndGivenName("Müller", "Hans")).isNotEmpty();
        assertThat(findByFamilyNameAndGivenName("Mueller", "Hans")).isNotEmpty();
        assertThat(findByFamilyNameAndGivenName("Mueller", "Hanz")).isNotEmpty();

        assertThat(findByFamilyNameAndGivenName("Müll", "Han")).isNotEmpty();

        assertThat(findByFamilyNameAndGivenName("Mei", "")).isEmpty();
        assertThat(findByFamilyNameAndGivenName("Muell", "")).isEmpty();

        assertThat(findByFamilyNameAndGivenName("Hans", "Müller")).isEmpty();
        assertThat(findByFamilyNameAndGivenName("Michael", "Meyers")).isEmpty();

    }

    private List<PatientEo> findByFamilyNameAndGivenName(String familyName, String givenName) {
        System.out.println("");
        System.out.println("searching for: " + familyName + ", " + givenName);
        var patients = patientLogic.findByFamilyNameAndGivenName(familyName,givenName);
        if (patients.isEmpty()) {
            System.out.println("not found");
        } else {
            System.out.println("found: " +  patients.get(0).toString());
        }
        return patients;
    }

}