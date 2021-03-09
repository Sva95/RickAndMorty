package com.example.testapi.presentation.screen.character_profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.testapi.R
import com.example.testapi.presentation.entity.CharacterProfileEntity
import com.example.testapi.util.CharacterProfileUiState
import kotlinx.android.synthetic.main.activity_character_profile.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CharacterProfileFragment : Fragment(R.layout.activity_character_profile) {

    private val args: CharacterProfileFragmentArgs by navArgs()
    private val viewModel: CharacterProfileViewModel by viewModel { parametersOf(args.characterId) }


    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    is CharacterProfileUiState.Success -> showContent(it.profile)
                    is CharacterProfileUiState.Loading -> progressContent()
                    is CharacterProfileUiState.Error -> hideContent()
                }
            }
        }
    }

    private fun showContent(characterProfileEntity: CharacterProfileEntity) {
        //  loadImage(characterProfileEntity.imgUrl)
        txt_character_profile_name.setText(characterProfileEntity.name)
        txt_character_planet_name.setText(characterProfileEntity.locationEntity)
        txt_character_species_name.setText(characterProfileEntity.species)
        txt_character_status_name.setText(characterProfileEntity.status)
        pb_character_profile.setVisibility(View.INVISIBLE)
        layout_error.setVisibility(View.GONE)
        ll_profile_screen.setVisibility(View.VISIBLE)
        if (characterProfileEntity.status.equals("dead")) {
            //  img_character_profile_status.setBackground(resources.getDrawable(R.drawable.ic_status_dead_orange))
            //  txt_character_status_name.setTextColor(resources.getColor(R.color.colorOrange))
        }
    }

    private fun hideContent() {
        pb_character_profile.setVisibility(View.INVISIBLE)
        ll_profile_screen.setVisibility(View.INVISIBLE)
        layout_error.setVisibility(View.VISIBLE)
    }

    private fun progressContent() {
        layout_error.setVisibility(View.INVISIBLE)
        pb_character_profile.setVisibility(View.VISIBLE)
        ll_profile_screen.setVisibility(View.INVISIBLE)
    }


}