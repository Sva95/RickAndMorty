package com.example.testapi.presentation.adapter.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.testapi.base.BaseViewHolder
import com.example.testapi.presentation.entity.CharacterEntity
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterViewHolder(private val view: View) : BaseViewHolder<CharacterEntity>(view) {

    override fun bind(item: CharacterEntity, callback: ((Int) -> Unit)?) {
        with(view){
            txt_character_name.text = item.name
            txt_character_status.text = item.status
            txt_character_species.text = item.species
            txt_character_planet.text = item.location
        }

        Glide.with(view.context)
            .load(item.img_url)
            .apply(RequestOptions().circleCrop())
            .override(180, 180)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view.img_character)
    }
}