package ru.samirkad.kadrecipe.repository

import androidx.lifecycle.LiveData
import ru.samirkad.kadrecipe.dto.Category
import ru.samirkad.kadrecipe.dto.RecipeDto


interface RecipeRepository {

    val data: LiveData<List<RecipeDto>>

    fun save(recipe: RecipeDto)
    fun delete(recipeId: Long)
    fun addToFavourites(recipeId: Long)
    fun search(recipeName: String)
    fun getCategory(category: Category)
    fun update()

    companion object {
        const val NEW_RECIPE_ID = 0L
    }
}