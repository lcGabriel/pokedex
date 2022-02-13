package br.com.lj.pokedex_android.domain

data class PokemonSpecies (
    val gender_rate: Int,
    val genera: List<Genus>,
    val flavor_text_entries: List<FlavorTextEntries>
)

class Genus(
    val genus: String
)

class FlavorTextEntries(
    val flavor_text: String
)
