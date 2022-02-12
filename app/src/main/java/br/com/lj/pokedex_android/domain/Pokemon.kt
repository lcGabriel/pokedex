package br.com.lj.pokedex_android.domain

data class Pokemon(
    val height : Int,
    val number : Int,
    val name : String,
    val weight : Int,
    val abilities: List<AbilitiesPokemon>,
    val stats : List<Stats>,
    val types : List<PokemonType>
){
    val formatterNumber = number.toString().padStart(3, '0')
    val formatterName = name.capitalize()

    class Stats(
        val base_stat: Int,
        val stat: StatsPokemon
    )

    val imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$formatterNumber.png"
}

