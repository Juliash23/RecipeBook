package ru.samirkad.kadrecipe.model

import ru.samirkad.kadrecipe.dto.RecipeDto

fun RecipeEntity.toModel() = RecipeDto(
    // мы не можем вытащить данные по названию колонки, а только по ее ид(или индексу)
    id = id,
    name = name,
    author = author,
    content = content,
    category = category,
    addToFavourites = addToFavourites,
    foodImage = foodImage
)

fun RecipeDto.toEntity() = RecipeEntity(
    id = id,
    name = name,
    author = author,
    content = content,
    category = category,
    addToFavourites = addToFavourites,
    foodImage = foodImage
)