package com.example.testapi.presentation.screen.character_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.testapi.R

class CharacterProfileFragment : Fragment(R.layout.activity_character_profile) {

    val args: CharacterProfileFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("ARRRRGS ${args}")
    }
}