package edu.byu.mealplanningassistant.resources

import edu.byu.mealplanningassistant.models.Recipe

class Recipes {
    companion object {
        public val breakfast1 = Recipe(
            "Breakfast 1", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "easy", "clean", "breakfast"),
            null, null, null, null
        )
        public val breakfast2 = Recipe(
            "Breakfast 2", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "vegan", "clean", "breakfast"),
            null, null, null, null
        )
        public val breakfast3 = Recipe(
            "Breakfast 3", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "vegan", "breakfast"),
            null, null, null, null
        )

        public val lunch1 = Recipe(
            "Lunch 1", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "easy", "clean", "lunch"),
            null, null, null, null
        )
        public val lunch2 = Recipe(
            "Lunch 2", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "vegan", "clean", "lunch"),
            null, null, null, null
        )
        public val lunch3 = Recipe(
            "Lunch 3", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "vegan", "lunch"),
            null, null, null, null
        )


        public val dinner1 = Recipe(
            "Dinner 1", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "easy", "clean", "dinner"),
            null, null, null, null
        )
        public val dinner2 = Recipe(
            "Dinner 2", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "vegan", "clean", "dinner"),
            null, null, null, null
        )
        public val dinner3 = Recipe(
            "Dinner 3", "test", 123456,
            hashMapOf(), listOf(), hashSetOf("ovenless", "vegan", "dinner"),
            null, null, null, null
        )
    }
}