package org.goafabric.llm.dbagent.persistence

import org.goafabric.llm.dbagent.logic.PersonLogic
import org.goafabric.llm.dbagent.persistence.entity.Address
import org.goafabric.llm.dbagent.persistence.entity.Allergy
import org.goafabric.llm.dbagent.persistence.entity.Person
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component
import java.util.List
import java.util.function.IntConsumer
import java.util.stream.IntStream

@Component
class DemoDataImporter(private val applicationContext: ApplicationContext) : CommandLineRunner {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun run(vararg args: String) {
        if ((args.size > 0) && ("-check-integrity" == args[0])) {
            return
        }

        importDemoData()
    }

    private fun importDemoData() {
        try {
            if (applicationContext.getBean<PersonLogic>(PersonLogic::class.java)!!.findAll().isEmpty()) {
                insertData()
                log.info("demo data import done ...")
            }
        } catch (e: DataAccessException) {
            insertData()
        }
    }

    private fun insertData() {
        IntStream.range(0, 1).forEach(IntConsumer { i: Int ->
            applicationContext.getBean<PersonLogic>(PersonLogic::class.java)!!.save(
                Person(
                    null, null, "Homer", "Sampson",
                    List.of<Address>(createAddress("Evergreen Terrace No. " + i)), createAllergy("Work")
                )
            )
            applicationContext.getBean<PersonLogic>(PersonLogic::class.java)!!.save(
                Person(
                    null, null, "Bart", "Sampson",
                    List.of<Address>(createAddress("Everblue Terrace No. " + i)), createAllergy("Peanuts")
                )
            )

            applicationContext.getBean<PersonLogic>(PersonLogic::class.java)!!.save(
                Person(
                    null, null, "Monty", "Burns",
                    List.of<Address>(
                        Address(
                            null,
                            null,
                            "Mammon Street No. 1000 on the corner of Croesus",
                            "Shelbyville"
                        )
                    ), createAllergy("Bees")
                )
            )
            applicationContext.getBean<PersonLogic>(PersonLogic::class.java)!!.save(
                Person(
                    null, null, "Ned", "Flanders",
                    List.of<Address>(Address(null, null, "Flanders street", "Springfield")), createAllergy("Bees")
                )
            )
        })
    }

    private fun createAddress(street: String): Address {
        return Address(null, null, street, "Springfield")
    }

    private fun createAllergy(allergy: String): MutableList<Allergy> {
        return List.of<Allergy>(Allergy(null, null, allergy))
    }
}
