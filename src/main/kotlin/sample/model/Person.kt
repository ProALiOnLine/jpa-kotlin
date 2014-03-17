package sample.model

import javax.persistence.Entity as entity
import javax.persistence.GeneratedValue as generatedValue
import javax.persistence.Id as identifier
import java.io.Serializable

/**
 * @author me
 */

entity
public class Person(var name: String = "", var age: Int = 0): Serializable {

    identifier
    generatedValue
    public var id: Long = 0

    override public fun toString(): String = "[$id-($name , $age)]"
}