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

    fun post(createRecipeRequest: CreateRecipeRequest): Response{
        db.addRecipe(createRecipeRequest.getRecipe())
        return Response(true, "")
    }

    fun mealPlanRequest(request: GetRandomizedRecipeBatchRequest) : GetRandomizedRecipeBatchResponse {
        val breakfast = db.getRecipesWithTags((request.requests[0].tags.toHashSet() + hashSetOf("breakfast")) as HashSet<String>)
        val lunch = db.getRecipesWithTags((request.requests[0].tags.toHashSet() + hashSetOf("lunch")) as HashSet<String>)
        val dinner = db.getRecipesWithTags((request.requests[0].tags.toHashSet() + hashSetOf("dinner")) as HashSet<String>)

        val bld = listOf(breakfast, lunch, dinner).map { recipeList -> recipeList.filter { recipe ->
            request.requests[0].ingredients?.any { requestedIngredient -> recipe.ingredients.keys.any{ recipeIngredient ->
                recipeIngredient.contains(requestedIngredient) || requestedIngredient.contains(recipeIngredient)
            }} ?: true
        }}

        return GetRandomizedRecipeBatchResponse(bld[0], bld[1], bld[2], true, "")
    }

    fun getAllRecipes(): List<Recipe> {
        return db.getAllRecipes()
    }
}
