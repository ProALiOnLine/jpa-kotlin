package sample.model

import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.Persistence

/**
 * @author me
 */
class PersistenceSpec extends Specification {
    EntityManager em

    void setup() {
        em = Persistence
                .createEntityManagerFactory("sample-persistence-unit")
                .createEntityManager()

        inTransaction {
            em.createQuery("DELETE FROM Person").executeUpdate()
        }
    }

    void cleanup() {
        em.close()
    }

    def "save and find count"() {
        doSave(3)

        when:
        def result = em.createQuery("SELECT COUNT(p) FROM Person p", Long).getSingleResult()

        then:
        result == 3
    }

    def "save and find all"() {
        doSave(3)

        when:
        def list = em.createQuery("SELECT p FROM Person p", Person).getResultList()

        then:
        list.size() == 3
        list.each { assert it.getId() != null }
    }

    private def doSave(count) {
        inTransaction {
            (1..count).each { em.persist(new Person("my name #$it", it + 18)) }
        }
    }

    private def inTransaction(Closure closure) {
        em.getTransaction().begin()
        closure.call()
        em.getTransaction().commit()
    }
}
