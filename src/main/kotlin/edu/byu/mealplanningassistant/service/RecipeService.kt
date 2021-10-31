package edu.byu.mealplanningassistant.service

import edu.byu.mealplanningassistant.database.RecipeDAO
import edu.byu.mealplanningassistant.models.*
import java.time.Instant
import java.util.*

class RecipeService (
    val recipeDAO: RecipeDAO,
) {
    //Creates a recipe and successfully adds it to the database. Returns response of true/false success and message
    //Maybe throws an exception if ... Invalid request data (missing values, invalid values, etc.), Internal server error ????
    fun createRecipeRequest(recipeRequest: CreateRecipeRequest): Response? {
        try {
            recipeDAO.addRecipe(
                Recipe(
                    id = "R_${UUID.randomUUID()}",
                    name = recipeRequest.name,
                    owner = recipeRequest.owner,
                    date = Instant.now().toEpochMilli(),
                    ingredients = recipeRequest.ingredients,
                    instructions = recipeRequest.instructions,
                    tags = recipeRequest.tags.toSet(),
                    servingsProduced = recipeRequest.servingsProduced,
                    calories = recipeRequest.calories,
                    macronutrients = recipeRequest.macronutrients,
                    rating = recipeRequest.rating,
                )
            )
            return Response(true)
        } catch (e: Exception) {
            return Response(false, e.toString())
        }
    }

    //successfully retrieves a randomized batch from database and Returns GetRandomizedRecipeBatchResponse with a list of recipes, true/false success, and message
    //Maybe throws an exception if ... Invalid request data (missing values, invalid values, etc.), Internal server error ????
    fun getRandomizedBatchRequest(randomizedBatchRequest: GetRandomizedRecipeBatchRequest):
            GetRandomizedRecipeBatchResponse? {
        TODO("Not yet implemented")
    }
}