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

    fun getRecipesWithTags(tagSet: HashSet<String>, mealType: String) : List<Recipe>{
        val filter = `all`("tags", tagSet)
        return getFromDB(filter)
    }

    fun getRecipes() : List<Recipe> {
        val filter = exists("_id")
        return getFromDB(filter)
    }

    private fun getFromDB(filter: Bson) : List<Recipe>{
        val collection = getDatabase().getCollection("recipes")
        val iter = collection.find(filter).iterator()
        val recipes = Vector<Recipe>()
        while(iter.hasNext()){
            val curRecipe = iter.next()
            val name = curRecipe.getString("name")
            val owner = curRecipe.getString("owner")
            val date =  curRecipe.getLong("date")
            val ingredientDoc = (curRecipe["ingredients"] as Document)
            val ingredients = ingredientDocToMap(ingredientDoc)
            val instructions = (curRecipe["instructions"] as ArrayList<String>)
            val tags = HashSet<String>(curRecipe["tags"] as ArrayList<String>)
            val servings = curRecipe.getInteger("servings")
            val calories = curRecipe.getInteger("calories")
            val macrosDoc = curRecipe.getInteger("macronutrients")
            var macros = HashMap<Macronutrient, Number>()
            if (macrosDoc != null){
                macros = macrosDocToMap((macrosDoc as Document))
            }
            var rating = 0.0
            try {
                rating = curRecipe.getDouble("rating")
            }
            catch (ignore: Exception){
                //we get this error if rating is null which is okay but a double cannot be null
                // so we just have to eat the error and say that it's rated 0
            }
            val recipe = Recipe(name, owner, date, ingredients, instructions as List<String>, tags,
                servings, calories, macros, rating)
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
}