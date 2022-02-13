package br.com.lj.pokedex_android.api.model

import br.com.lj.pokedex_android.domain.PokemonFlavorText
import br.com.lj.pokedex_android.domain.PokemonGenus

data class PokemonsSpeciesApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonSpecieResult>
)

data class PokemonSpeciesApiResult(
    val gender_rate: Int,
    val genera: List<PokemonGenus>,
    val flavor_text_entries: List<PokemonFlavorText>
)

data class PokemonSpecieResult(
    val name: String,
    val url: String
)