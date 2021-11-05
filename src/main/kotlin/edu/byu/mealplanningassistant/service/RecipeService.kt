package edu.byu.mealplanningassistant.service

import edu.byu.mealplanningassistant.models.*
import edu.byu.mealplanningassistant.repository.RecipesRepository
import org.springframework.stereotype.Service

@Service
class RecipeService(val db: RecipesRepository) {

    fun findRecipes(): List<Recipe> = db.getRecipes()

    fun clear() : Response {
        db.clearDatabase()
        return Response(true, "")
    }

    fun post(recipe: Recipe): Response{
        db.addRecipe(recipe)
        return Response(true, "")
    }

    fun mealPlanRequest(request: GetRandomizedRecipeBatchRequest) : GetRandomizedRecipeBatchResponse {
        val breakfast = db.getRecipesWithTags(request.requests.get(0).tags as HashSet<String>, "breakfast")
        val lunch = db.getRecipesWithTags(request.requests.get(0).tags as HashSet<String>, "lunch")
        val dinner = db.getRecipesWithTags(request.requests.get(0).tags as HashSet<String>, "dinner")

        return GetRandomizedRecipeBatchResponse(breakfast, lunch, dinner, true, "")
    }
}
