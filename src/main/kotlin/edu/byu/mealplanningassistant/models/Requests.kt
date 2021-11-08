package edu.byu.mealplanningassistant.models

import java.time.Instant

open class Response(
    open val success: Boolean,
    open val message: String = "Unknown",
)

class CreateRecipeRequest (
    val name: String,
    val owner: String,
    val ingredients: List<IngredientAmount>,
    val instructions: List<Instruction>,
    val tags: List<String>,
    val servingsProduced: Number?,
    val calories: Number?,
    val macronutrients: Map<Macronutrient, Number>?,
    val rating: Number?,
) {
    class IngredientAmount(
        val ingredient: String,
        val amount: String,
    )

    class Instruction (
        val instruction: String
    )

    fun getRecipe() = Recipe(
        name = name,
        owner = owner,
        ingredients = ingredients.associate { it.ingredient to it.amount },
        instructions = instructions.map { it.instruction },
        tags = tags.toHashSet(),
        servings = servingsProduced,
        calories = calories,
        macronutrients = macronutrients,
        rating = rating,
        date = Instant.now().toEpochMilli(),
    )
}

class GetRandomizedRecipeBatchRequest (
    val requests: List<GetRandomizedRecipeRequest>
)

/**
 * @author Chris West
 * As part of the MVP, this will NOT be sent on its own from the client to the backend, but will instead be packed
 * into a list in GetRandomizedRecipeBatchRequest objects. Should we later decide that we want to allow the user to
 * request recipes individually, this can be used for that.
 */
class GetRandomizedRecipeRequest(
    /** the name of the meal will likely follow a format like "Monday-Dinner", "Tuesday-Breakfast", "Wednesday-Lunch, etc.
     * but this allows us to have the future flexibility of allow users to name their meals whatever they want and
     * then the backend doesn't have to care what they're called */
    val mealName: String,
    val tags : List<String>,
)

class GetRandomizedRecipeBatchResponse (
    val breakfast: List<Recipe>,
    val lunch: List<Recipe>,
    val dinner: List<Recipe>,
    override val success: Boolean,
    override val message: String
) : Response(success)