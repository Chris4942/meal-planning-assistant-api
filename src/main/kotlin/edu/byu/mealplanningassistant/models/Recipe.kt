package edu.byu.mealplanningassistant.models

import org.springframework.data.mongodb.core.mapping.Document
import kotlin.collections.HashSet

/**
 * @author Chris West (Chris4942)
 */
@Document
data class Recipe (
    val name: String,
    /** Refers to a username */
    val owner: String,
    /** date the recipe was put into the db. Intended to be used for prioritizing more recent recipes */
    val date: Long,
    /** ingredientName to amount */
    val ingredients: Map<String, String>,
    val instructions: List<String>,
    val tags: HashSet<String>,
    /** Number of people this recipe can serve */
    val servings: Number?,
    /** calories per serving */
    val calories: Number?,
    val macronutrients: Map<Macronutrient, Number>?,
    /** number between 1 and 5 inclusive. includes decimals */
    val rating: Number?,
)
enum class Macronutrient {
    FAT,
    TRANS_FAT,
    SAT_FAT,
    CARBOHYDRATES,
    SUGAR,
    PROTEIN,
    CHOLESTEROL,
    SODIUM,
    // TODO add more as needed
}