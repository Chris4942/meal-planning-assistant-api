package edu.byu.mealplanningassistant

import edu.byu.mealplanningassistant.models.*
import edu.byu.mealplanningassistant.service.RecipeService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class MealPlanningAssistantApplication

fun main(args: Array<String>) {
	runApplication<MealPlanningAssistantApplication>(*args)
}

@RestController
class RecipeResource(val service: RecipeService) {
	@GetMapping
	fun index(): List<Recipe> = service.findRecipes()

	@PostMapping("/recipe")
	fun addRecipe(@RequestBody recipeRequest: CreateRecipeRequest) : Response {
		return service.post(recipeRequest)
	}

	@PostMapping("/recipes")
	fun addRecipes(@RequestBody recipes: List<CreateRecipeRequest>) : Response {
		return recipes.map{service.post(it)}[0]
	}

	@DeleteMapping("/recipes")
	fun cleanDB() : Response {
		return service.clear()
	}

	@GetMapping("/meal-plan")
	fun getMealPlan(@RequestBody request: GetRandomizedRecipeBatchRequest) : GetRandomizedRecipeBatchResponse {
		return service.mealPlanRequest(request)
  	}

	@GetMapping("/recipes")
	fun getAllRecipes() = service.getAllRecipes()
}