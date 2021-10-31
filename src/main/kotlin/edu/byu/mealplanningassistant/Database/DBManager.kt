package edu.byu.mealplanningassistant.Database

import com.mongodb.*
import com.mongodb.client.MongoDatabase
import edu.byu.mealplanningassistant.models.Recipe
import org.bson.Document
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@EnableMongoRepositories(basePackages = ["com.concretepage.repository"])
class MongoDBConfig {
    val databaseName: String
        get() = "myMongoDB"

    @Bean
    fun mongoClient(): com.mongodb.MongoClient {
        val address = ServerAddress("127.0.0.1", 27017)
        val credential = MongoCredential.createCredential(
            "mdbUser",
            databaseName, "cp".toCharArray()
        )
        val options = MongoClientOptions.Builder().build()
        return MongoClient(address, credential, options)
    }

    @Bean
    fun mongoDbFactory(): SimpleMongoClientDatabaseFactory {
        return SimpleMongoClientDatabaseFactory(mongoClient(), databaseName)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoDbFactory())
    }
}

class DBManager{
    fun getDatabase() : MongoDatabase{
        val mongoClient = MongoClient("localhost")
        return mongoClient.getDatabase("mealManager")
    }

    fun addRecipe(newRecipe: Recipe){
        try {
            val collection = getDatabase().getCollection("recipes")
            val document = Document("_id", newRecipe.id)
                .append("name", newRecipe.name)
                .append("owner", newRecipe.owner)
                .append("date:", newRecipe.date)
                .append("ingredients", newRecipe.ingredients)
                .append("instructions", newRecipe.instructions)
                .append("tags", newRecipe.tags)
            collection.insertOne(document)
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

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
}