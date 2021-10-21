package edu.byu.mealplanningassistant.service

import edu.byu.mealplanningassistant.models.GetRandomizedRecipeBatchRequest
import edu.byu.mealplanningassistant.models.GetRandomizedRecipeBatchResponse

//successfully retrieves a randomized batch from database and Returns GetRandomizedRecipeBatchResponse with a list of recipes, true/false success, and message
//Maybe throws an exception if ... Invalid request data (missing values, invalid values, etc.), Internal server error ????
class GetRandomizedRecipeBatchService {

    fun get_randomized_batch_request(randomized_batch_request: GetRandomizedRecipeBatchRequest): GetRandomizedRecipeBatchResponse? {

    }
}