package br.com.lj.pokedex_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lj.pokedex_android.domain.Pokemon
import br.com.lj.pokedex_android.domain.PokemonType
import br.com.lj.pokedex_android.view.PokemonAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_pokemons)

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        val bulbasaur = Pokemon(
            "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/001.png",
            4,
            "Bulbasaur",
            listOf(
                PokemonType("Fire")
            )
        )
        val pokemons = listOf(
            bulbasaur, bulbasaur, bulbasaur, bulbasaur, bulbasaur, bulbasaur
        )

        recyclerView.adapter = PokemonAdapter(pokemons)
    }
}