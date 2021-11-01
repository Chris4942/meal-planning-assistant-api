package edu.byu.mealplanningassistant

import edu.byu.mealplanningassistant.database.RecipeDAO
import edu.byu.mealplanningassistant.models.Recipe
import kotlin.collections.HashMap
import kotlin.collections.HashSet

// When this is compiled, this will be put into a class so that it can run on the JVM
fun main() {
    println("This will be the main entry point to the api.")
//    addTestToDB()
}

fun addTestToDB(){
    val db = RecipeDAO()
    val ingr = HashMap<String, String>()
    ingr["chicken"] = "10 grams"
    val instr = listOf("cook chicken", "serve chicken")
    val tags = HashSet<String>()
    tags.add("meat")
    tags.add("quick")
//    tags.add("billy")
    val rec1 = Recipe("1", "new recipe", "Tony", 10000, ingr, instr,
        tags, null, null, null, null)
    val rec2 = Recipe("2", "new recipe2", "Tony", 10000, ingr, instr,
        tags, null, null, null, null)
    val rec3 = Recipe("3", "new recipe", "Bill", 10000, ingr, instr,
        tags, null, null, null, null)
//    db.addRecipe(rec1)
//    db.addRecipe(rec2)
//    db.addRecipe(rec3)
//    tags.remove("quick")
    print(db.getRecipesWithTags(tags))
//    db.clearDatabase()
//    db.deleteFromFieldOnValue("owner", "Tony")
}