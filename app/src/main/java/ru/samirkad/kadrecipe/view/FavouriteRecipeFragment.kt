package ru.samirkad.kadrecipe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.samirkad.kadrecipe.adapter.RecipesAdapter
import ru.samirkad.kadrecipe.databinding.FavouriteFragmentBinding
import ru.samirkad.kadrecipe.viewModel.RecipeViewModel

class FavouriteRecipeFragment : Fragment() {

    private val favouriteRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavouriteFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipesAdapter(favouriteRecipeViewModel)
        binding.recipesRecycler.adapter = adapter

        favouriteRecipeViewModel.data.observe(viewLifecycleOwner) { recipes ->

            val favouriteRecipes = recipes.filter { it.addToFavourites }
            adapter.submitList(favouriteRecipes)

            val emptyList = recipes.none() { it.addToFavourites }
            binding.textEmptyList.visibility =
                if(emptyList) View.VISIBLE else View.GONE
            binding.iconEmptyList.visibility =
                if(emptyList) View.VISIBLE else View.GONE
        }

        favouriteRecipeViewModel.separateRecipeViewEvent.observe(viewLifecycleOwner) { recipeCardId ->
            val direction = Fav





        }

    }

}