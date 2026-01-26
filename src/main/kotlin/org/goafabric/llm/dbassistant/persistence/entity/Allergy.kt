package org.goafabric.llm.dbassistant.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "allergy")
class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null
        private set

    @Version
    var version: Long? = null
        private set

    var allergy: String? = null
        private set

    constructor(id: String?, version: Long?, allergy: String?) {
        this.id = id
        this.version = version
        this.allergy = allergy
    }

    internal constructor()

    override fun toString(): String {
        return "AllergyEo{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", allergy='" + allergy + '\'' +
                '}'
    }
}
