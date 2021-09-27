package edu.byu.mealplanningassistant.services

import edu.byu.mealplanningassistant.models.GetRandomizedRecipesRequest
import edu.byu.mealplanningassistant.models.GetRandomizedRecipesResponse

class MealPlanGenerator {
    fun getRandomizedRecipes(getRandomizedRecipesRequest: GetRandomizedRecipesRequest) : GetRandomizedRecipesResponse {
        TODO()
        /**
         * queryRecordsInRandomOrder().forEach {
         *      if it.matches(getRandomizedRecipeRequests.requests.any()) {
         *          assign(it to the first recipe request that matches that has no recipes assigned)
         *      }
         *      if all recipe requests are filled {
         *          break
         *      }
         * }
         *
         * format in a GetRandomizedRecipesResponse and return
         */
    }
}