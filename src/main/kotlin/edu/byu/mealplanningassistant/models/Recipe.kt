package edu.byu.mealplanningassistant.models

import java.util.*

/**
 * @author Chris West (Chris4942)
 *
 * data makes it so the compiler implements a toString() method and an equals() method for us
 * Putting ()s after the class name allows for defining a default constructor
 * "val"s and "var"s defined in the primary constructor will become member variables on the object
 * "val"s and "var"s are public by default (but "val"s are read only, so there's no issue with that) so there is usually no need for getters and setters
 * It is GOOD practice to avoid using "var"s whenever possible
 */
data class Recipe (
    val name: String, // <val|var> <varName> : <Type>
    val attributes: SortedSet<String> // val means that the variable is final. Use var for variables that need to change
        ){

    // fun <functionName> (<argumentList>): <ReturnType> {<methodBody>}
    // the '?' after "String: means that it can be null. Without that, a variable cannot be null which means that no null check needs to be performed
    // Look up Kotlin Null handling for more details
    // This function can be deleted later. I'm not attached to it in any way.
    fun hasAttribute(attribute: String?): Boolean {
        return attributes.contains(attribute)
    }
}