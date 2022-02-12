package br.com.lj.pokedex_android.api.model

import br.com.lj.pokedex_android.domain.AbilitiesPokemon
import br.com.lj.pokedex_android.domain.PokemonType
import br.com.lj.pokedex_android.domain.StatsPokemon

data class PokemonsApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class PokemonApiResult(
    val id: Int,
    val height: Int,
    val name: String,
    val abilities: List<PokemonAbilitiesSlot>,
    val stats: List<PokemonStats>,
    val types: List<PokemonTypeSlot>,
    val weight: Int
)

data class PokemonAbilitiesSlot(
    val ability: AbilitiesPokemon,
    val is_hidden: Boolean,
    val slot: Int,
)

data class PokemonStats(
    val base_stat: Int,
    val effort: Int,
    val stat: StatsPokemon,
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)

