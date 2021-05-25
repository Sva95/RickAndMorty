package com.example.testapi.util

data class CharacterFilter(
    var name: String = "",
    var filterNamePosition: Int = 0,
    var status: String = "",
    var filterStatusPosition: Int = 0,
    var species: String = "",
    var filterSpeciesPosition: Int = 0
)

class CharacterFilterCapsule {

    private val _userFilter = CharacterFilter()
    fun getFilter() = _userFilter

    fun setFilterName(name: String) {
        _userFilter.name = name
    }

    fun setFilterStatus(characterFilter: CharacterFilter) {
        if (characterFilter.status != _userFilter.status) {
            val status =
                if (characterFilter.filterStatusPosition == 0) "" else characterFilter.status
            _userFilter.status = status
            _userFilter.filterStatusPosition = characterFilter.filterStatusPosition
        }
    }

    fun setFilterSpecies(characterFilter: CharacterFilter) {
        if (characterFilter.species != _userFilter.species) {
            val species =
                if (characterFilter.filterSpeciesPosition == 0) "" else characterFilter.species
            _userFilter.species = species
            _userFilter.filterSpeciesPosition = characterFilter.filterSpeciesPosition
        }
    }
}

