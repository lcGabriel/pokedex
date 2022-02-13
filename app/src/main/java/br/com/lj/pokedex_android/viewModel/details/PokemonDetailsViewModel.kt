package br.com.lj.pokedex_android.viewModel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.lj.pokedex_android.api.PokemonRepository
import br.com.lj.pokedex_android.domain.FlavorTextEntries
import br.com.lj.pokedex_android.domain.Genus
import br.com.lj.pokedex_android.domain.PokemonSpecies

class PokemonDetailsViewModel: ViewModel()  {
    var pokemonsSpecies = MutableLiveData<List<PokemonSpecies?>>()

    init {
        Thread {
            loadPokemonsSpecies()
        }.start()
    }

    private fun loadPokemonsSpecies() {
        val pokemonsApiResult = PokemonRepository.listPokemonsSpecies()

        pokemonsApiResult?.results?.let {

            pokemonsSpecies.postValue(it.map { pokemonResult ->
                val number = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon-species/", "")
                    .replace("/", "").toInt()

                val pokemonApiResult = PokemonRepository.getPokemonSpecies(number)

                pokemonApiResult?.let {
                    PokemonSpecies(
                        pokemonApiResult.gender_rate,
                        pokemonApiResult.genera.map { genera ->
                            Genus(
                                genera.genus
                            )
                        },
                        pokemonApiResult.flavor_text_entries.map { flavor ->
                            FlavorTextEntries(
                                flavor.flavor_text
                            )
                        },
                    )
                }
            })
        }
    }

}