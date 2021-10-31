package edu.byu.mealplanningassistant

import edu.byu.mealplanningassistant.models.GetRandomizedRecipeBatchRequest
import edu.byu.mealplanningassistant.models.Recipe
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

	@PostMapping
	fun post(@RequestBody recipe: Recipe) {
		service.post(recipe)
	}

	@PatchMapping
	fun patch(@RequestBody request: GetRandomizedRecipeBatchRequest){
		//return service.mealPlanRequest(request)
		TODO("Implement")
	}
}