package org.goafabric.llm.dbassistant.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "person")
class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null
        private set

    @Version //optimistic locking
    var version: Long? = null
        private set

    var firstName: String? = null
        private set

    var lastName: String? = null
        private set

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    var address: MutableList<Address> = mutableListOf()
        private set

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    var allergy: MutableList<Allergy> = mutableListOf()
        private set


    constructor(
        id: String?,
        version: Long?,
        firstName: String?,
        lastName: String?,
        address: MutableList<Address>,
        allergy: MutableList<Allergy>
    ) {
        this.id = id
        this.version = version
        this.firstName = firstName
        this.lastName = lastName
        this.address = address
        this.allergy = allergy
    }

    internal constructor()

    override fun toString(): String {
        return "PersonEo{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", allergy=" + allergy +
                '}'
    }
}
