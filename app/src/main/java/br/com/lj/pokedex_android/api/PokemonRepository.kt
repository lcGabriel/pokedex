package br.com.lj.pokedex_android.api

import br.com.lj.pokedex_android.api.model.PokemonApiResult
import br.com.lj.pokedex_android.api.model.PokemonSpeciesApiResult
import br.com.lj.pokedex_android.api.model.PokemonsApiResult
import br.com.lj.pokedex_android.api.model.PokemonsSpeciesApiResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {
    private val service: PokemonService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(PokemonService::class.java)
    }

    fun listPokemons(limit: Int = 151): PokemonsApiResult? {
        val call = service.listPokemons(limit)

        return call.execute().body()
    }

    fun listPokemonsSpecies(limit: Int = 151): PokemonsSpeciesApiResult? {
        val call = service.listPokemonsSpecies(limit)

        return call.execute().body()
    }

    fun getPokemon(number: Int): PokemonApiResult? {
        val call = service.getPokemon(number)

        return call.execute().body()
    }

    fun getPokemonSpecies(number: Int): PokemonSpeciesApiResult? {
        val call = service.getSpeciesPokemon(number)

        return call.execute().body()
    }
}