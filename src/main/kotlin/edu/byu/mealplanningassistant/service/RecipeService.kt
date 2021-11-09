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
        val breakfast = mutableListOf<Recipe>()
        val lunch = mutableListOf<Recipe>()
        val dinner = mutableListOf<Recipe>()
        request.requests.forEach{r ->
            if(r.mealName.startsWith("breakfast")){
                getRecipe("breakfast", breakfast, r.tags)
            }
            else if(r.mealName.startsWith("lunch")){
                getRecipe("lunch", lunch, r.tags)
            }
            else if(r.mealName.startsWith("dinner")){
                getRecipe("dinner", dinner, r.tags)
            }
            else {
                println("Unknown meal type " +  r.mealName)
            }
        }
        return GetRandomizedRecipeBatchResponse(breakfast, lunch, dinner, true, "")
    }

    private fun getRecipe(meal: String, mealRecipes: MutableList<Recipe>, tags: List<String>) {
        var recipes = db.getRecipesWithTags((tags.toHashSet() + hashSetOf(meal)) as HashSet<String>)
        if (recipes.isEmpty()){
            recipes = db.getRecipesWithTags(hashSetOf(meal))
        }
        var added = false
        for( recipe in recipes) {
            if (!mealRecipes.contains(recipe)) {
                mealRecipes.add(recipe)
                added = true
                break //can only do this in a loop which is why we're not doing a forEach here
            }
        }
        if (!added){
            mealRecipes.add(recipes[0]) // there aren't enough unique recipes for us, we'll have to duplicate
        }
    }

    fun getAllRecipes(): List<Recipe> {
        return db.getAllRecipes()
    }
}
