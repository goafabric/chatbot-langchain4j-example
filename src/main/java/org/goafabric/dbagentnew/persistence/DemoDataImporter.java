package org.goafabric.dbagentnew.persistence;

import org.goafabric.dbagentnew.logic.PersonLogic;
import org.goafabric.dbagentnew.persistence.entity.Address;
import org.goafabric.dbagentnew.persistence.entity.Allergy;
import org.goafabric.dbagentnew.persistence.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class DemoDataImporter implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ApplicationContext applicationContext;

    public DemoDataImporter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        if ((args.length > 0) && ("-check-integrity".equals(args[0]))) { return; }

        importDemoData();
    }

    private void importDemoData() {
        try {
            if (applicationContext.getBean(PersonLogic.class).findAll().isEmpty()) {
                insertData();
                log.info("demo data import done ...");
            }
        } catch (DataAccessException e) {
            insertData();
        }
    }

    private void insertData() {
        IntStream.range(0, 1).forEach(i -> {
            applicationContext.getBean(PersonLogic.class).save(new Person(null, null, "Homer", "Sampson"
                    , List.of(createAddress("Evergreen Terrace No. " + i)), createAllergy("Work")));

            applicationContext.getBean(PersonLogic.class).save(new Person(null, null, "Bart", "Sampson"
                    , List.of(createAddress("Everblue Terrace No. " + i)), createAllergy("Peanuts")));

            applicationContext.getBean(PersonLogic.class).save(new Person(null, null, "Monty", "Burns"
                    , List.of(new Address(null, null, "Mammon Street No. 1000 on the corner of Croesus", "Shelbyville")), createAllergy("Bees")));
        });

    }

    private Address createAddress(String street) {
        return new Address(null, null, street, "Springfield");
    }

    private List<Allergy> createAllergy(String allergy) {
        return List.of(new Allergy(null, null, allergy));
    }

}
