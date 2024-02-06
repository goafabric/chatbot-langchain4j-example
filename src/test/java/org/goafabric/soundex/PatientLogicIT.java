package org.goafabric.soundex;

import org.goafabric.soundex.logic.PatientLogic;
import org.goafabric.soundex.repository.PatientEo;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public void init() {
        patientLogic.save(new PatientEo(null, "Homer", null, "Simpson", null));
        patientLogic.save(new PatientEo(null, "Monty", null,"Burns", null));
        patientLogic.save(new PatientEo(null, "Michael", null,"Meyers", null));
        patientLogic.save(new PatientEo(null, "Hans", null, "Müller", null));

    }

    @Test
    public void test() {
        assertThat(findBy("Meyers", "")).isNotEmpty();
        assertThat(findBy("Meiers", "")).isNotEmpty();

        assertThat(findBy("Meyers", "Michael")).isNotEmpty();
        assertThat(findBy("Meiers", "Michael")).isNotEmpty();

        assertThat(findBy("Mey", "Mic")).isNotEmpty();

        assertThat(findBy("Müller", "Hans")).isNotEmpty();
        assertThat(findBy("Mueller", "Hans")).isNotEmpty();
        assertThat(findBy("Mueller", "Hanz")).isNotEmpty();

        assertThat(findBy("Müll", "Han")).isNotEmpty();

        assertThat(findBy("Mei", "")).isEmpty();
        assertThat(findBy("Muell", "")).isEmpty();

        assertThat(findBy("Hans", "Müller")).isEmpty();
        assertThat(findBy("Michael", "Meyers")).isEmpty();

    }

    private List<PatientEo> findBy(String familyName, String givenName) {
        System.out.println("");
        System.out.println("searching for: " + familyName + ", " + givenName);
        var patients = patientLogic.findBy(familyName,givenName);
        if (patients.isEmpty()) {
            System.out.println("not found");
        } else {
            System.out.println("found: " +  patients.get(0).toString());
        }
        return patients;
    }

}