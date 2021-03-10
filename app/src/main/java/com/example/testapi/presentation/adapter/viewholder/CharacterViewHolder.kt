package com.example.testapi.presentation.adapter.viewholder

import android.view.View
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.testapi.base.BaseViewHolder
import com.example.testapi.presentation.entity.CharacterUiEntity
import com.example.testapi.presentation.screen.character.CharacterFragmentDirections
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterViewHolder(private val view: View) : BaseViewHolder<CharacterUiEntity>(view) {

    override fun bind(item: CharacterUiEntity, callback: (() -> Unit)?) {
        with(view) {
            txt_character_name.text = item.name
            txt_character_status.text = item.status
            txt_character_species.text = item.species
            txt_character_planet.text = item.location

            img_character.setOnClickListener {
                val userId = item.id
                val action =
                    CharacterFragmentDirections.actionCharacterFragmentToCharacterProfileFragment(
                        userId
                    )
                it.findNavController().navigate(action)
            }
        }


        Glide.with(view.context)
            .load(item.img_url)
            .apply(RequestOptions().circleCrop())
            .override(180, 180)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view.img_character)
    }
}