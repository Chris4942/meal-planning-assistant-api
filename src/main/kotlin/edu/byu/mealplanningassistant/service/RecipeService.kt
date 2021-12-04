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
        println("Start of mealPlanRequest")
        val breakfast = mutableListOf<Recipe>()
        val lunch = mutableListOf<Recipe>()
        val dinner = mutableListOf<Recipe>()
        request.requests.forEach{r ->
            when {
                r.mealName.startsWith("breakfast") -> getRecipe("breakfast", breakfast, r.tags, r.ingredients)
                r.mealName.startsWith("lunch") -> getRecipe("lunch", lunch, r.tags, r.ingredients)
                r.mealName.startsWith("dinner") -> getRecipe("dinner", dinner, r.tags, r.ingredients)
                else -> println("Unknown meal type ${r.mealName}")
            }
        }
        return GetRandomizedRecipeBatchResponse(breakfast, lunch, dinner, true, "")
    }

    private fun getRecipe(meal: String, mealRecipes: MutableList<Recipe>, tags: List<String>, ingredients: List<String>?) {
        println(ingredients)
        var recipes = if (ingredients == null)
            db.getRecipesWithTags((tags.toHashSet() + hashSetOf(meal)) as HashSet<String>)
        else {
            db.getRecipesWithTagsAndIngredients(
                (tags.toHashSet() + hashSetOf(meal)) as HashSet<String>,
                ingredients.toHashSet()
            )
        }
        if (recipes.isEmpty()){
            recipes = db.getRecipesWithTags(hashSetOf(meal))
        }
        recipes.find { recipe -> !mealRecipes.contains(recipe) }?.also { mealRecipes.add(it) } ?: mealRecipes.add(recipes[0])
    }

    fun getAllRecipes(): List<Recipe> {
        return db.getAllRecipes()
    }
}
