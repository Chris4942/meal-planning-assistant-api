package edu.byu.mealplanningassistant.service

import edu.byu.mealplanningassistant.database.RecipeDAO
import edu.byu.mealplanningassistant.models.*
import edu.byu.mealplanningassistant.repository.RecipesRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RecipeService(
    val recipeDao: RecipeDAO,
    ) {

    init {
        println("I am initialized")
    }

    fun findRecipes(): List<Recipe> {
        return listOf(Recipe(
            "test",
            "me",
            Instant.now().toEpochMilli(),
            mapOf(),
            listOf(),
            hashSetOf(),
            5,
            200,
            null,
            5
        ))
//        return db.getRecipes()
    }

    fun clear() : Response {
        recipeDao.clearDatabase()
        return Response(true, "")
    }

    fun post(recipe: Recipe): Response{
        recipeDao.addRecipe(recipe)
        return Response(true, "")
    }

    fun mealPlanRequest(request: GetRandomizedRecipeBatchRequest) : GetRandomizedRecipeBatchResponse {
        val breakfast = recipeDao.getRecipesWithTags((request.requests.get(0).tags.toHashSet() + hashSetOf("breakfast")) as HashSet<String>)
        val lunch = recipeDao.getRecipesWithTags((request.requests.get(0).tags.toHashSet() + hashSetOf("lunch")) as HashSet<String>)
        val dinner = recipeDao.getRecipesWithTags((request.requests.get(0).tags.toHashSet() + hashSetOf("dinner")) as HashSet<String>)

        return GetRandomizedRecipeBatchResponse(breakfast, lunch, dinner, true, "")
    }
}
