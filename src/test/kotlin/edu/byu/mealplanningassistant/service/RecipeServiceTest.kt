package edu.byu.mealplanningassistant.service

import com.mongodb.internal.connection.tlschannel.util.Util.assertTrue
import edu.byu.mealplanningassistant.database.RecipeDAO
import edu.byu.mealplanningassistant.models.CreateRecipeRequest
import edu.byu.mealplanningassistant.models.Recipe
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.*


class RecipeServiceTest {
    companion object {
        const val NAME = "NAME"
        const val OWNER = "OWNER"
        const val SERVINGS_PRODUCED = 7
        const val CALORIES = 789
    }

     private lateinit var recipeService: RecipeService // normally if you try to create a not ? variable without setting it, the compiler will throw a fit, but if you say lateinit var it's like pinky promising the compiler that you'll make sure that it gets set to not null before it's used
     private lateinit var recipeDAO : RecipeDAO

     @Before
     fun setup() {
         println("setup")
        recipeDAO = Mockito.mock(RecipeDAO::class.java)
        recipeService = RecipeService(recipeDAO)
    }

    @Test
    fun createRecipeRequest() {
        println("test1")
        val createRecipeRequest = CreateRecipeRequest(
            name = NAME,
            owner = OWNER,
            ingredients = mapOf(
                "eggs" to "one",
                "milk" to "one cup",
                "dark chocolate" to "3/4 bushels",
            ),
            instructions = listOf(
                "Put the uncracked egg in the milk",
                "figure out what a bushel is",
                "Give up on making this nonsensical recipe :)",
            ),
            tags = listOf("ovenless", "less than 15 minutes", "probably pointless"),
            servingsProduced = SERVINGS_PRODUCED,
            calories = CALORIES,
            macronutrients = null,
            rating = null,
        )
        val response = recipeService.createRecipeRequest(createRecipeRequest)
        assertTrue(response!!.success)
        verify(recipeDAO, times(1))::addRecipe // TODO further validation should be done on this test to make sure it's working properly
    }

    @Test
    fun getRandomizedBatchRequest() {
    }
}