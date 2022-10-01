package ru.samirkad.kadrecipe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.samirkad.kadrecipe.R
import ru.samirkad.kadrecipe.adapter.showCategories
import ru.samirkad.kadrecipe.databinding.NewRecipeFragmentBinding
import ru.samirkad.kadrecipe.model.Category
import ru.samirkad.kadrecipe.model.RecipeDto
import ru.samirkad.kadrecipe.repository.RecipeRepository
import ru.samirkad.kadrecipe.viewModel.RecipeViewModel

class NewOrEditRecipe : Fragment() {

    private val args by navArgs<NewOrEditRecipeArgs>()

    private val newOrEditRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = NewRecipeFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val thisRecipe = args.currentRecipe
        if(thisRecipe != null) {
            with(binding) {
                name.setText(thisRecipe.name)
                content.setText(thisRecipe.content)
                categoryRecipeCheckBox.check(R.id.checkBoxEuropean) // по умолчанию ставится
                checkBoxEuropean.text = checkBoxEuropean.context.showCategories(Category.European)
                checkBoxAsian.text = checkBoxAsian.context.showCategories(Category.Asian)
                checkBoxEastern.text = checkBoxEastern.context.showCategories(Category.Eastern)
                checkBoxRussian.text = checkBoxRussian.context.showCategories(Category.Russian)
                checkBoxAmerican.text = checkBoxAmerican.context.showCategories(Category.American)
            }
        }

        binding.name.requestFocus()

        binding.categoryRecipeCheckBox.setOnCheckedChangeListener {_, _ ->
            getCheckedCategory(binding.categoryRecipeCheckBox.checkedRadioButtonId)
        }
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: NewRecipeFragmentBinding) {
        val currentRecipe = RecipeDto(
            id = args.currentRecipe?.id ?: RecipeRepository.NEW_RECIPE_ID,
            name = binding.name.text.toString(),
            content = binding.content.text.toString(),
            category = getCheckedCategory(binding.categoryRecipeCheckBox.checkedRadioButtonId)
        )
        if(emptyFieldsCheck(recipe = currentRecipe)) {
            val resultBundle = Bundle(1)
            resultBundle.putParcelable(RESULT_KEY, resultBundle)
            setFragmentResult(REQUEST_KEY, resultBundle)
            findNavController().popBackStack()
        }
    }

    private fun getCheckedCategory(checkedId: Int) = when (checkedId) {
        R.id.checkBoxEuropean -> Category.European
        R.id.checkBoxAsian -> Category.Asian
        R.id.checkBoxEastern -> Category.Eastern
        R.id.checkBoxRussian -> Category.Russian
        R.id.checkBoxAmerican -> Category.American
        else -> throw java.lang.IllegalArgumentException("Unknown type: $checkedId")
    }

    private fun emptyFieldsCheck(recipe: RecipeDto): Boolean {
        return if (recipe.name.isBlank() && recipe.content.isBlank()) {
            Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_LONG).show()
            false
        } else true
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "newContent"
    }
}

