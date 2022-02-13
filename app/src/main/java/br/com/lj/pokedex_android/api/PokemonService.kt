package br.com.lj.pokedex_android.api

import br.com.lj.pokedex_android.api.model.PokemonApiResult
import br.com.lj.pokedex_android.api.model.PokemonSpeciesApiResult
import br.com.lj.pokedex_android.api.model.PokemonsApiResult
import br.com.lj.pokedex_android.api.model.PokemonsSpeciesApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int): Call<PokemonsApiResult>

    @GET("pokemon-species")
    fun listPokemonsSpecies(@Query("limit") limit: Int): Call<PokemonsSpeciesApiResult>

    @GET("pokemon/{number}")
    fun getPokemon(@Path("number") number: Int): Call<PokemonApiResult>

    @GET("pokemon-species")
    fun getSpeciesPokemon(@Path("number") number: Int): Call<PokemonSpeciesApiResult>
}