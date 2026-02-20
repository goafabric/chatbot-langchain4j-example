package org.goafabric.llm.tool.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "address")
class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null
        private set

    @Version //optimistic locking
    var version: Long? = null
        private set

    var street: String? = null
        private set
    var city: String? = null
        private set

    constructor(id: String?, version: Long?, street: String?, city: String?) {
        this.id = id
        this.street = street
        this.city = city
        this.version = version
    }

    internal constructor()

    override fun toString(): String {
        return "AddressEo{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}'
    }
}
