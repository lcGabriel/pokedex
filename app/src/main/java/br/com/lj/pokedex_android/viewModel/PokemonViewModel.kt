package br.com.lj.pokedex_android.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.lj.pokedex_android.api.PokemonRepository
import br.com.lj.pokedex_android.domain.Pokemon

class PokemonViewModel : ViewModel() {
    var pokemons = MutableLiveData<List<Pokemon?>>()
    var listNamePokemon = ArrayList<String>()

    init {
        Thread {
            loadPokemons()
        }.start()
    }

    private fun loadPokemons() {
        val pokemonsApiResult = PokemonRepository.listPokemons()

        pokemonsApiResult?.results?.let {

            pokemons.postValue(it.map { pokemonResult ->
                val number = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                val pokemonApiResult = PokemonRepository.getPokemon(number)

                pokemonApiResult?.let {
                    Pokemon(
                        pokemonApiResult.height,
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.weight,
                        pokemonApiResult.abilities.map { abilities ->
                            abilities.ability
                        },
                        pokemonApiResult.stats.map { status ->
                            Pokemon.Stats(
                                status.base_stat,
                                status.stat
                            )
                        },
                        pokemonApiResult.types.map { type ->
                            type.type
                        }
                    )
                }
            })
        }
    }

    fun reloadList(position: Int, listName: ArrayList<String>): ArrayList<Pokemon> {
        val pokeList = ArrayList<Pokemon>()
        var count = 0
        for (poke in pokemons.value!!) {
            if (!checkPokemonName(listName, poke!!)) {
                if (position == count) {
                    break
                }
                pokeList.add(poke)
                listNamePokemon.add(poke.name)
                count++
            } else {
                listNamePokemon.add(poke.name)
                pokeList.add(poke)
            }
        }
        return pokeList
    }

    fun checkPokemonName(listNamePokemon: List<String>, pokemon: Pokemon): Boolean {
        for (pokemonName in listNamePokemon) {
            if(pokemon.name == pokemonName){
                return true
            }
        }
        return false
    }
}