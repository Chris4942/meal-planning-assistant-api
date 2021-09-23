package edu.byu.mealplanningassistant.models

class CreateRecipeRequest (
    val name: String,
    val instructions: String,
    val attributes: List<String>,
)

class AddAttributeToRecipeRequest (
    val id: String,
    val attribute: String,
)

class RemoveAttributeFromRecipeRequest (
    val id: String,
    val attribute: String,
)

class DeleteRecipeRequest (
    val id: String,
)

class GetRandomizedRecipeRequest(
    val day : String,
    val meal : String,
    val attributes : List<String>,
)

class GetRandomizedRecipesRequest (
    val requests: List<GetRandomizedRecipeRequest>
)

class GetRandomizedRecipesResponse (
    val recipes: List<Recipe>,
)

class GetRandomizedRecipeResponse (
    val recipe: Recipe,
)