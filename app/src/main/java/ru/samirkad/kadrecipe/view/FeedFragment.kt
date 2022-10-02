package ru.samirkad.kadrecipe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.samirkad.kadrecipe.adapter.RecipesAdapter
import ru.samirkad.kadrecipe.databinding.FeedFragmentBinding
import ru.samirkad.kadrecipe.model.Category
import ru.samirkad.kadrecipe.model.RecipeDto
import ru.samirkad.kadrecipe.viewModel.RecipeViewModel

class FeedFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateRecipe.observe(this) { recipe ->
            val direction = FeedFragmentDirections.actionFeedFragmentToNewRecipeFragment(recipe)
            findNavController().navigate(direction)
        }
    }

    override fun onResume() {
        super.onResume()

        setFragmentResultListener(
            requestKey = NewOrEditRecipe.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != NewOrEditRecipe.REQUEST_KEY) return@setFragmentResultListener
            val newRecipe = bundle.getParcelable<RecipeDto>(
                NewOrEditRecipe.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newRecipe)
        }

        setFragmentResultListener(
            requestKey = CategoryFilterFragment.CHECKBOX_KEY
        ) { requestKey, bundle ->
            if(requestKey != CategoryFilterFragment.CHECKBOX_KEY) return@setFragmentResultListener
            val categories = bundle.getParcelableArrayList<Category>(
                CategoryFilterFragment.CHECKBOX_KEY
            ) ?: return@setFragmentResultListener
            viewModel.showRecipesCategories(categories)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddButtonClicked()
        }

        if(viewModel.setCategoryFilter) {
            binding.undo.isVisible = viewModel.setCategoryFilter
            binding.fab.visibility = View.GONE
            binding.undo.setOnClickListener {
                viewModel.showRecipesCategories(Category.values().toList())
                viewModel.setCategoryFilter = false
                binding.undo.visibility = View.GONE
                binding.fab.visibility = View.VISIBLE
                viewModel.data.observe(viewLifecycleOwner) { recipes ->
                    adapter.submitList(recipes)
                }
            }
        } else {
            binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener
            {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText.isNullOrEmpty()) {
                        adapter.submitList(viewModel.data.value)
                        return true
                    }
                    var recipeList = adapter.currentList
                    recipeList = recipeList.filter { recipe ->
                        recipe.name.lowercase().contains(newText.lowercase())
                    }
                    viewModel.searchRecipeByName(newText)

                    if(recipeList.isEmpty()) {
                        Toast.makeText(context, "Ничего нет", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.submitList(recipeList)
                    }
                    return true
                }
            })
        }
        viewModel.separateRecipeViewEvent.observe(viewLifecycleOwner) { recipeCardId ->
            binding.search.setQuery("", false)
            val direction =
                FeedFragmentDirections.actionFeedFragmentToSeparateRecipeFragment(recipeCardId)
            findNavController().navigate(direction)
        }
    }.root
}