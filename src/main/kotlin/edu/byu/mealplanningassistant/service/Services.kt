package edu.byu.mealplanningassistant.service

import edu.byu.mealplanningassistant.models.CreateRecipeRequest
import edu.byu.mealplanningassistant.models.GetRandomizedRecipeBatchRequest
import edu.byu.mealplanningassistant.models.GetRandomizedRecipeBatchResponse
import edu.byu.mealplanningassistant.models.Response

class Services {
    //Creates a recipe and successfully adds it to the database. Returns response of true/false success and message
    //Maybe throws an exception if ... Invalid request data (missing values, invalid values, etc.), Internal server error ????
    fun createRecipeRequest(recipeRequest: CreateRecipeRequest): Response? {
        TODO("Not yet implemented")
    }

    //successfully retrieves a randomized batch from database and Returns GetRandomizedRecipeBatchResponse with a list of recipes, true/false success, and message
    //Maybe throws an exception if ... Invalid request data (missing values, invalid values, etc.), Internal server error ????
    fun getRandomizedBatchRequest(randomizedBatchRequest: GetRandomizedRecipeBatchRequest):
            GetRandomizedRecipeBatchResponse? {
        TODO("Not yet implemented")
    }
}