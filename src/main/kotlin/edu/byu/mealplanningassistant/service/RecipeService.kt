package edu.byu.mealplanningassistant.service

import com.google.gson.Gson
import edu.byu.mealplanningassistant.models.*
import edu.byu.mealplanningassistant.repository.RecipesRepository
import org.springframework.stereotype.Service

@Service
class RecipeService(val db: RecipesRepository) {

    fun findRecipes(): List<Recipe> = db.findRecipes().map {dbToRecipe(it)}

    fun post(recipe: Recipe){
        db.save(recipeToDB(recipe))
    }

    fun mealPlanRequest(request: GetRandomizedRecipeBatchRequest) : GetRandomizedRecipeBatchResponse {
        //List<Recipe> recipes = db.findRecipes(request.requests.get(0).tags)
        //return GetRandomizedRecipeBatchResponse(recipes, True, "")
        TODO("IMPLEMENT!")
    }

    fun recipeToDB(recipe: Recipe): DBRecipe {
        val gson = Gson()
        val ingredients = gson.toJson(recipe.ingredients).toString()
        val instructions = gson.toJson(recipe.instructions).toString()
        val tags = gson.toJson(recipe.tags).toString()
        val macronutrients = gson.toJson(recipe.macronutrients).toString()
        return DBRecipe(recipe.id, recipe.name, recipe.owner,
        recipe.date, ingredients, instructions, tags, recipe.servings,
            recipe.calories, macronutrients, recipe.rating
        )
    }

    fun dbToRecipe(recipe:DBRecipe) : Recipe {
        val gson = Gson()
        val ingredients = gson.fromJson(recipe.ingredients, Map::class.java)
        val instructions = gson.fromJson(recipe.instructions, List::class.java)
        val tags = gson.fromJson(recipe.tags, HashSet::class.java)
        val macronutrients = gson.fromJson(recipe.macronutrients, Map::class.java)
        return Recipe(recipe.id, recipe.name, recipe.owner,
            recipe.date, ingredients as Map<String, String>, instructions as List<String>,
            tags as HashSet<String>, recipe.servings,
            recipe.calories, macronutrients as Map<Macronutrient, Number>?, recipe.rating
        )
    }
}
