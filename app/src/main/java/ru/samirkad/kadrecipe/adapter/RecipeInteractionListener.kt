package ru.samirkad.kadrecipe.adapter

import ru.samirkad.kadrecipe.dto.RecipeDto


interface RecipeInteractionListener {

    fun onRemoveButtonClicked(recipe: RecipeDto)
    fun onEditButtonClicked(recipe: RecipeDto)
    fun onRecipeCardClicked(recipe: RecipeDto)
    fun onFavouritesButtonClicked(recipe: RecipeDto)
    fun onRecipeItemClicked(recipe: RecipeDto)
}