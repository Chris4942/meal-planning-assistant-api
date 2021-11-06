package edu.byu.mealplanningassistant.repository

import com.mongodb.*
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.*
import edu.byu.mealplanningassistant.models.Macronutrient
import edu.byu.mealplanningassistant.models.Recipe
import org.bson.Document
import org.bson.conversions.Bson
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Repository
class RecipesRepository{
    private fun getDatabase() : MongoDatabase{
        val mongoClient = MongoClient("localhost")
        return mongoClient.getDatabase("mealManager")
    }

    fun clearDatabase(){
        val filter = exists("_id")
        deleteFromDatabase(filter)
    }

    fun deleteFromFieldOnValue(field : String, value : Any){
        val filter = eq(field, value)
        deleteFromDatabase(filter)
    }

    fun deleteFromDatabase(filter: Bson) {
        val collection = getDatabase().getCollection("recipes")
        collection.deleteMany(filter)
    }

    fun addRecipe(newRecipe: Recipe){
        try {
            val collection = getDatabase().getCollection("recipes")
            val document = Document("name", newRecipe.name)
                .append("owner", newRecipe.owner)
                .append("date", newRecipe.date)
                .append("ingredients", newRecipe.ingredients)
                .append("instructions", newRecipe.instructions)
                .append("tags", newRecipe.tags)
                .append("servings", newRecipe.servings)
                .append("calories", newRecipe.calories)
                .append("macronutrients", newRecipe.macronutrients)
                .append("rating", newRecipe.rating)
            collection.insertOne(document)
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun getRecipesWithTags(tagSet: HashSet<String>) = getFromDB(`all`("tags", tagSet))

    // TODO I'm not sure if this is supposed to do something different than getAllRecipes later... I don't think it's pertinent to decide this now, but should discuss and consolidate if possible later
    fun getRecipes() = getFromDB()

    private fun getFromDB(filter: Bson? = null) : List<Recipe>{
        println("filter : $filter")
        val collection = getDatabase().getCollection("recipes")
        val recipes = mutableListOf<Recipe>()
        (if (filter != null) collection.find(filter).iterator() else collection.find().iterator())
            .forEach{ curRecipe ->
                val name = curRecipe.getString("name")
                val owner = curRecipe.getString("owner")
                val date =  curRecipe.getLong("date")
                val ingredientDoc = (curRecipe["ingredients"] as Document)
                val ingredients = ingredientDocToMap(ingredientDoc)
                val instructions = (curRecipe["instructions"] as ArrayList<String>)
                val tags = HashSet<String>(curRecipe["tags"] as ArrayList<String>)
                val servings = curRecipe.getInteger("servings")
                val calories = curRecipe.getInteger("calories")
                val macros = curRecipe.getInteger("macronutrients").let { macrosDoc ->
                    if (macrosDoc != null){
                        macrosDocToMap((macrosDoc as Document))
                    } else {
                        emptyMap()
                    }
                }
                val rating = try { curRecipe.getDouble("rating") } catch (ignore: Exception){ null }
                val recipe = Recipe(name, owner, date, ingredients, instructions as List<String>, tags, servings, calories, macros, rating)
                recipes.add(recipe)
        }
        return recipes
    }


    private fun macrosDocToMap(document: Document) : HashMap<Macronutrient, Number>{
        val macros = HashMap<Macronutrient, Number>()
        val macrosIter = document.iterator()
        while(macrosIter.hasNext()){
            val nextMacro = macrosIter.next()
            macros[nextMacro.key as Macronutrient] = (nextMacro.value as Number)
        }
        return macros
    }

    private fun ingredientDocToMap(document: Document) : HashMap<String, String>{
        val ingredients = HashMap<String, String>()
        val ingredIter = document.iterator()
        while(ingredIter.hasNext()){
            val nextIng = ingredIter.next()
            ingredients[nextIng.key] = (nextIng.value as String)
        }
        return ingredients
    }

    fun getAllRecipes(): List<Recipe> {
        return getFromDB()
    }
}