package br.com.lj.pokedex_android.domain

data class Pokemon(
    val number : Int,
    val name : String,
    val types : List<PokemonType>
){
    val formatterNumber = number.toString().padStart(3, '0')
    val formatterName = name.capitalize()


    val imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$formatterNumber.png"
}

