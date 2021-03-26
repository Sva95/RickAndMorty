package com.example.testapi.presentation.screen.character_profile

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.testapi.R
import com.example.testapi.base.BaseFragment
import com.example.testapi.databinding.FragmentCharacterProfileBinding
import com.example.testapi.presentation.entity.CharacterProfileEntity
import com.example.testapi.util.CharacterProfileUiState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CharacterProfileFragment :
    BaseFragment<FragmentCharacterProfileBinding>(FragmentCharacterProfileBinding::inflate) {

    private val args: CharacterProfileFragmentArgs by navArgs()
    private val viewModel: CharacterProfileViewModel by viewModel { parametersOf(args.characterId) }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRetryContent.setOnClickListener {
            viewModel.retry()
        }

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
        loadImage(characterProfileEntity.imgUrl)
        with(binding) {
            txtCharacterProfileName.setText(characterProfileEntity.name)
            txtCharacterPlanetName.setText(characterProfileEntity.locationEntity)
            txtCharacterSpeciesName.setText(characterProfileEntity.species)
            txtCharacterStatusName.setText(characterProfileEntity.status)
            pbCharacterProfile.setVisibility(View.INVISIBLE)
            layoutError.setVisibility(View.GONE)
            llProfileScreen.setVisibility(View.VISIBLE)
        }
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .skipMemoryCache(true)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgCharacterProfile)
    }


    private fun hideContent() {
        with(binding) {
            pbCharacterProfile.setVisibility(View.INVISIBLE)
            llProfileScreen.setVisibility(View.INVISIBLE)
            layoutError.setVisibility(View.VISIBLE)
        }
    }

    private fun progressContent() {
        with(binding) {
            layoutError.setVisibility(View.INVISIBLE)
            pbCharacterProfile.setVisibility(View.VISIBLE)
            llProfileScreen.setVisibility(View.INVISIBLE)
        }
    }
}