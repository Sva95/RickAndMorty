package com.example.testapi.presentation.adapter.viewholder

import android.view.View
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.testapi.base.BaseViewHolder
import com.example.testapi.databinding.ItemCharacterBinding
import com.example.testapi.presentation.entity.CharacterUiEntity
import com.example.testapi.presentation.screen.character.CharacterFragmentDirections


class CharacterViewHolder(private val view: ItemCharacterBinding) : BaseViewHolder<CharacterUiEntity>(view) {

    override fun bind(item: CharacterUiEntity, callback: (() -> Unit)?) {
        with(view) {
            txtCharacterName.text = item.name
            txtCharacterStatus.text = item.status
            txtCharacterSpecies.text = item.species
            txtCharacterPlanet.text = item.location

            imgCharacter.setOnClickListener {
                val userId = item.id
                val action =
                    CharacterFragmentDirections.actionCharacterFragmentToCharacterProfileFragment(
                        userId
                    )
                it.findNavController().navigate(action)
            }
        }

        Glide.with(view.root.context)
            .load(item.img_url)
            .apply(RequestOptions().circleCrop())
            .override(180, 180)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view.imgCharacter)
    }
}