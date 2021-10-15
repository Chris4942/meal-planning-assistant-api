package edu.byu.mealplanningassistant.service

import edu.byu.mealplanningassistant.models.CreateRecipeRequest
import edu.byu.mealplanningassistant.models.Response

//Creates a recipe and successfully adds it to the database. Returns response of true/false success and message
//Maybe throws an exception if ... Invalid request data (missing values, invalid values, etc.), Internal server error ????
class CreateRecipeRequestService {

    fun create_recipe_request(recipe_request: CreateRecipeRequest): Response? {

    }
}