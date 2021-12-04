package edu.byu.mealplanningassistant.repository

import com.mongodb.*
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.*
import com.mongodb.client.model.Sorts
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

    fun getRecipesWithTags(tagSet: HashSet<String>, sort : Boolean = false) = getFromDB(`all`("tags", tagSet), sort)

    fun getRecipesWithTagsAndIngredients(tagSet: HashSet<String>, ingredientSet: HashSet<String>, sort : Boolean = false) =
        getFromDB(and(`all`("tags", tagSet), `all`("ingredients", ingredientSet)), sort)


    // TODO I'm not sure if this is supposed to do something different than getAllRecipes later... I don't think it's pertinent to decide this now, but should discuss and consolidate if possible later
    fun getRecipes() = getFromDB(null, false)

    private fun getQueryResults(filter: Bson? = null, sort : Boolean) : Iterator<Document>{
        val collection = getDatabase().getCollection("recipes")
        return if(sort){
            (if (filter != null) collection.find(filter).sort(Sorts.descending("rating")).iterator() else collection.find().sort(Sorts.descending("rating")).iterator())
        } else{
            (if (filter != null) collection.find(filter).sort(Sorts.descending("rating")).iterator() else collection.find().sort(Sorts.descending("rating")).iterator())
        }
    }

    private fun getFromDB(filter: Bson? = null, sort : Boolean) : List<Recipe>{
        println("filter : $filter")

        val recipes = mutableListOf<Recipe>()
        (getQueryResults(filter, sort))
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
                var rating = try { curRecipe.getDouble("rating") } catch (ignore: Exception){ null }
                if (rating == null) {
                    rating = try { curRecipe.getInteger("rating").toDouble() } catch (ignore: Exception){ null }
                }
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
        return getFromDB(null, false)
    }
}