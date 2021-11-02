package edu.byu.mealplanningassistant

import edu.byu.mealplanningassistant.models.GetRandomizedRecipeBatchRequest
import edu.byu.mealplanningassistant.models.GetRandomizedRecipeBatchResponse
import edu.byu.mealplanningassistant.models.Recipe
import edu.byu.mealplanningassistant.models.Response
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
	fun addRecipe(@RequestBody recipe: Recipe) : Response {
		return service.post(recipe)
	}

	@PostMapping("/recipes")
	fun addRecipes(@RequestBody recipes: List<Recipe>) : Response {
		return recipes.map{service.post(it)}[0]
	}

	@DeleteMapping("/recipes")
	fun cleanDB() : Response {
		return service.clear()
	}

	@PatchMapping("/meal-plan")
	fun getMealPlan(@RequestBody request: GetRandomizedRecipeBatchRequest) : GetRandomizedRecipeBatchResponse {
		return service.mealPlanRequest(request)
  }
}