package br.com.lj.pokedex_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lj.pokedex_android.api.PokemonRepository
import br.com.lj.pokedex_android.domain.Pokemon
import br.com.lj.pokedex_android.domain.PokemonType
import br.com.lj.pokedex_android.view.PokemonAdapter

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.rv_pokemons)

        /* val bulbasaur = Pokemon(
             "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/001.png",
             4,
             "Bulbasaur",
             listOf(
                 PokemonType("Fire")
             )
         )*//*
        val pokemons = listOf(
            bulbasaur, bulbasaur, bulbasaur, bulbasaur, bulbasaur, bulbasaur
        )*/
        Thread(Runnable {
            loadPokemons()
        }).start()
    }

    private fun loadPokemons() {

        val pokemonsApiResult = PokemonRepository.listPokemons()

        pokemonsApiResult?.results?.let {

            val pokemons: List<Pokemon?> = it.map { pokemonResult ->
                val number = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                val pokemonApiResult = PokemonRepository.getPokemon(number)

                pokemonApiResult?.let {
                    Pokemon(
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.types.map { type ->
                            type.type
                        }
                    )
                }
            }

            val layoutManager = LinearLayoutManager(this)

            recyclerView.post {
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = PokemonAdapter(pokemons)
            }
        }
    }
}