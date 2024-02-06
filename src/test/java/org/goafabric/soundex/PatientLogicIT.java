package org.goafabric.soundex;

import org.goafabric.soundex.repository.PatientEo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientLogicIT {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatientLogic patientLogic;

    @BeforeAll
    public void init() {
        patientLogic.save(new PatientEo(null, "Homer", "Simpson", null));
        patientLogic.save(new PatientEo(null, "Monty", "Burns", null));
        patientLogic.save(new PatientEo(null, "Michael", "Meyers", null));
        patientLogic.save(new PatientEo(null, "Ernst", "Müller", null));

    }

    @Test
    public void test() {
        //patientLogic.findAll().forEach(patient -> log.info(patient.toString()));
        
        patientLogic.findBy("Simpson").forEach(patient -> log.info(patient.toString()));

    }

}