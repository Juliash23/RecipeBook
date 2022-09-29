package ru.samirkad.kadrecipe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.samirkad.kadrecipe.viewModel.RecipeViewModel

class SeparateRecipeFragment : Fragment() {

    private val args by navArgs<SeparateRecipeFragment>()

    private val separateRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = Sep {

    }

    override fun onResume() {
        super.onResume()

        setFragmentResultListener(
            requestKey = NewOrEditRecipe.RE
        )
    }
}