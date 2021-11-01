package edu.byu.mealplanningassistant.database

import com.mongodb.*
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.*
import edu.byu.mealplanningassistant.models.Macronutrient
import edu.byu.mealplanningassistant.models.Recipe
import org.bson.Document
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

open class RecipeDAO{
    private fun getDatabase() : MongoDatabase{
        val mongoClient = MongoClient("localhost")
        return mongoClient.getDatabase("mealManager")
    }

    fun clearDatabase(){
        val collection = getDatabase().getCollection("recipes")
        val filter = exists("_id")
        collection.deleteMany(filter)
    }

    fun deleteFromFieldOnValue(field : String, value : Any){
        val collection = getDatabase().getCollection("recipes")
        val filter = eq(field, value)
        collection.deleteMany(filter)
    }


    fun addRecipe(newRecipe: Recipe){
        try {
            val collection = getDatabase().getCollection("recipes")
            val document = Document("_id", newRecipe.id)
                .append("name", newRecipe.name)
                .append("owner", newRecipe.owner)
                .append("date", newRecipe.date)
                .append("ingredients", newRecipe.ingredients)
                .append("instructions", newRecipe.instructions)
                .append("tags", newRecipe.tags)
            collection.insertOne(document)
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun getRecipesWithTags(tagSet: HashSet<String>) : Vector<Recipe>{
        val collection = getDatabase().getCollection("recipes")
        val filter = `all`("tags", tagSet)
        val iter = collection.find(filter).iterator()
        val recipes = Vector<Recipe>()

        while(iter.hasNext()){
            val curRecipe = iter.next()
            val id = curRecipe.getString("_id")
            val name = curRecipe.getString("name")
            val owner = curRecipe.getString("owner")
            val date =  curRecipe.getLong("date")
            val ingredientDoc = (curRecipe.get("ingredients") as Document)
            val ingredients = ingredientDocToMap(ingredientDoc)
            val instructions = (curRecipe.get("instructions") as ArrayList<String>)
            val tags = HashSet<String>(curRecipe.get("tags") as ArrayList<String>)
            val servings = curRecipe.getInteger("servingsProduced")
            val calories = curRecipe.getInteger("calories")
            val macrosDoc = curRecipe.getInteger("macronutrients")
            var macros = HashMap<Macronutrient, Number>()
            if (macrosDoc != null){
                macros = macrosDocToMap((macrosDoc as Document))
            }
            val rating = curRecipe.getInteger("rating")
            val recipe = Recipe(id, name, owner, date, ingredients, instructions as List<String>, tags,
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