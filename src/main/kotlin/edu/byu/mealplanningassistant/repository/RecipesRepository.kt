package edu.byu.mealplanningassistant.repository;

import edu.byu.mealplanningassistant.models.DBRecipe
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface RecipesRepository : CrudRepository<DBRecipe, String>{

    @Query("select * from recipes")
    fun findRecipes(): List<DBRecipe>

    @Query("select * from recipes")
    fun findRecipes(tags: List<String>) : List<DBRecipe>
/*
//THIS IS FROM TONY'S DBManager
    fun getRecipeTag(tagSet: HashSet<String>){
        val collection = getDatabase().getCollection("recipes")
        val query = BasicDBObject()
        val query2 = Query().addCriteria(Criteria().`in`("tags").isEqualTo(tagSet))
        query.put("tags", tagSet)
        val recipes = collection.find(query)
        val iter = recipes.iterator()
        while(iter.hasNext()){
            println(iter.next())
        }
        val client = MongoClient("localhost")
        val mongoTemplate = MongoTemplate(client, "mealManager")
        val entities = MongoTemplate
    }

 */
}



