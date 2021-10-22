package edu.byu.mealplanningassistant

import edu.byu.mealplanningassistant.Database.DBManager
import edu.byu.mealplanningassistant.models.Recipe
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

// When this is compiled, this will be put into a class so that it can run on the JVM
fun main() {
    println("This will be the main entry point to the api.")
    addTestToDB()
}

fun addTestToDB(){
    val db = DBManager()
    val ingr = HashMap<String, String>()
    ingr["chicken"] = "10 grams"
    val instr = listOf("cook chicken", "serve chicken")
    val tags = HashSet<String>()
    tags.add("meat")
    tags.add("quick")

    val rec = Recipe("1", "new recipe", "Tony", 10000, ingr, instr,
        tags, null, null, null, null)
    db.addRecipe(rec)
//    tags.remove("quick")
    db.getRecipeTag(tags)
}