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
        val breakfast = db.getRecipesWithTags((request.requests[0].tags.toHashSet() + hashSetOf("breakfast")) as HashSet<String>)
        val lunch = db.getRecipesWithTags((request.requests[0].tags.toHashSet() + hashSetOf("lunch")) as HashSet<String>)
        val dinner = db.getRecipesWithTags((request.requests[0].tags.toHashSet() + hashSetOf("dinner")) as HashSet<String>)

        return GetRandomizedRecipeBatchResponse(breakfast, lunch, dinner, true, "")
    }

    fun getAllRecipes(): List<Recipe> {
        return db.getAllRecipes()
    }
}
