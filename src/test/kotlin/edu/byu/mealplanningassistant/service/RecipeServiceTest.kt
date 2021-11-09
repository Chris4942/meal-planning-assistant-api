package edu.byu.mealplanningassistant.service

import edu.byu.mealplanningassistant.models.GetRandomizedRecipeBatchRequest
import edu.byu.mealplanningassistant.models.GetRandomizedRecipeRequest
import edu.byu.mealplanningassistant.repository.RecipesRepository
import edu.byu.mealplanningassistant.resources.Recipes
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class RecipeServiceTest {

    @Test
    fun getBreakfast(){
        val repository = Mockito.mock(RecipesRepository::class.java)
        val expTags = hashSetOf("ovenless", "easy", "under 30 minutes", "breakfast")
        val breakfastRecipes = mutableListOf(Recipes.breakfast1, Recipes.breakfast2, Recipes.breakfast3)
        Mockito.`when`(repository.getRecipesWithTags(expTags)).thenReturn(breakfastRecipes)
        val request = GetRandomizedRecipeBatchRequest(listOf(
            GetRandomizedRecipeRequest("breakfast-1", listOf("ovenless", "easy", "under 30 minutes")),
            GetRandomizedRecipeRequest("breakfast-2", listOf("ovenless", "easy", "under 30 minutes"))
        ))
        val service = RecipeService(repository)
        val response = service.mealPlanRequest(request)
        assert(response.breakfast.size == 2)
        assert(response.breakfast[0] == Recipes.breakfast1)
        assert(response.breakfast[1] == Recipes.breakfast2)
        assert(response.lunch.isEmpty())
        assert(response.dinner.isEmpty())

    }

    @Test
    fun getSome(){
        val repository = Mockito.mock(RecipesRepository::class.java)
        val expTags = hashSetOf("ovenless", "easy", "under 30 minutes", "lunch")
        val expTagsDinner = hashSetOf("ovenless", "easy", "under 30 minutes", "dinner")
        val lunchRecipes = mutableListOf(Recipes.lunch1, Recipes.lunch2, Recipes.lunch3)
        val dinnerRecipes = mutableListOf(Recipes.dinner1, Recipes.dinner2, Recipes.dinner3)
        Mockito.`when`(repository.getRecipesWithTags(expTags)).thenReturn(lunchRecipes)
        Mockito.`when`(repository.getRecipesWithTags(expTagsDinner)).thenReturn(dinnerRecipes)
        val request = GetRandomizedRecipeBatchRequest(listOf(
            GetRandomizedRecipeRequest("lunch-1", listOf("ovenless", "easy", "under 30 minutes")),
            GetRandomizedRecipeRequest("dinner-2", listOf("ovenless", "easy", "under 30 minutes"))
        ))
        val service = RecipeService(repository)
        val response = service.mealPlanRequest(request)
        assert(response.lunch.size == 1)
        assert(response.lunch[0] == Recipes.lunch1)
        assert(response.dinner.size == 1)
        assert(response.dinner[0] == Recipes.dinner1)
        assert(response.breakfast.isEmpty())
    }

    @Test
    fun getAll(){
        val repository = Mockito.mock(RecipesRepository::class.java)
        val expTags = hashSetOf("ovenless", "easy", "under 30 minutes", "breakfast")
        val expTagsLunch = hashSetOf("ovenless", "easy", "under 30 minutes", "lunch")
        val expTagsDinner = hashSetOf("ovenless", "easy", "under 30 minutes", "dinner")
        val breakfastRecipes = mutableListOf(Recipes.breakfast1, Recipes.breakfast2, Recipes.breakfast3)
        val lunchRecipes = mutableListOf(Recipes.lunch1, Recipes.lunch2, Recipes.lunch3)
        val dinnerRecipes = mutableListOf(Recipes.dinner1, Recipes.dinner2, Recipes.dinner3)
        Mockito.`when`(repository.getRecipesWithTags(expTags)).thenReturn(breakfastRecipes)
        Mockito.`when`(repository.getRecipesWithTags(expTagsLunch)).thenReturn(lunchRecipes)
        Mockito.`when`(repository.getRecipesWithTags(expTagsDinner)).thenReturn(dinnerRecipes)
        val request = GetRandomizedRecipeBatchRequest(listOf(
            GetRandomizedRecipeRequest("breakfast-1", listOf("ovenless", "easy", "under 30 minutes")),
            GetRandomizedRecipeRequest("lunch-1", listOf("ovenless", "easy", "under 30 minutes")),
            GetRandomizedRecipeRequest("lunch-2", listOf("ovenless", "easy", "under 30 minutes")),
            GetRandomizedRecipeRequest("dinner-2", listOf("ovenless", "easy", "under 30 minutes"))
        ))
        val service = RecipeService(repository)
        val response = service.mealPlanRequest(request)
        assert(response.lunch.size == 2)
        assert(response.lunch[0] == Recipes.lunch1)
        assert(response.lunch[1] == Recipes.lunch2)
        assert(response.dinner.size == 1)
        assert(response.dinner[0] == Recipes.dinner1)
        assert(response.breakfast.size == 1)
        assert(response.breakfast[0] == Recipes.breakfast1)
    }

    @Test
    fun noRecipe(){
        val repository = Mockito.mock(RecipesRepository::class.java)
        val expTags = hashSetOf("ovenless", "easy", "under 30 minutes", "breakfast")
        val expTagsLunch = hashSetOf("ovenless", "easy", "under 30 minutes", "lunch")
        val expTagsDinner = hashSetOf("ovenless", "easy", "under 30 minutes", "dinner")
        val breakfastRecipes = mutableListOf(Recipes.breakfast1, Recipes.breakfast2, Recipes.breakfast3)
        val lunchRecipes = mutableListOf(Recipes.lunch1)
        val dinnerRecipes = mutableListOf(Recipes.dinner1, Recipes.dinner2, Recipes.dinner3)
        Mockito.`when`(repository.getRecipesWithTags(expTags)).thenReturn(breakfastRecipes)
        Mockito.`when`(repository.getRecipesWithTags(expTagsLunch)).thenReturn(lunchRecipes)
        Mockito.`when`(repository.getRecipesWithTags(expTagsDinner)).thenReturn(dinnerRecipes)
        val request = GetRandomizedRecipeBatchRequest(listOf(
            GetRandomizedRecipeRequest("breakfast-1", listOf("ovenless", "easy", "under 30 minutes")),
            GetRandomizedRecipeRequest("lunch-1", listOf("ovenless", "easy", "under 30 minutes")),
            GetRandomizedRecipeRequest("lunch-2", listOf("ovenless", "easy", "under 30 minutes")),
            GetRandomizedRecipeRequest("dinner-2", listOf("ovenless", "easy", "under 30 minutes"))
        ))
        val service = RecipeService(repository)
        val response = service.mealPlanRequest(request)
        assert(response.lunch.size == 1)
        assert(response.lunch[0] == Recipes.lunch1)
        assert(response.dinner.size == 1)
        assert(response.dinner[0] == Recipes.dinner1)
        assert(response.breakfast.size == 1)
        assert(response.breakfast[0] == Recipes.breakfast1)
    }

    @Test
    fun noTags(){
        val repository = Mockito.mock(RecipesRepository::class.java)
        val expTags = hashSetOf("breakfast")
        val breakfastRecipes = mutableListOf(Recipes.breakfast1, Recipes.breakfast2, Recipes.breakfast3)
        Mockito.`when`(repository.getRecipesWithTags(expTags)).thenReturn(breakfastRecipes)
        val request = GetRandomizedRecipeBatchRequest(listOf(
            GetRandomizedRecipeRequest("breakfast-1", listOf()),
            GetRandomizedRecipeRequest("breakfast-2", listOf())
        ))
        val service = RecipeService(repository)
        val response = service.mealPlanRequest(request)
        assert(response.breakfast.size == 2)
        assert(response.breakfast[0] == Recipes.breakfast1)
        assert(response.breakfast[1] == Recipes.breakfast2)
        assert(response.lunch.isEmpty())
        assert(response.dinner.isEmpty())
    }
}