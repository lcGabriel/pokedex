package br.com.lj.pokedex_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lj.pokedex_android.api.PokemonRepository
import br.com.lj.pokedex_android.domain.Pokemon
import br.com.lj.pokedex_android.domain.PokemonType
import br.com.lj.pokedex_android.view.PokemonAdapter
import br.com.lj.pokedex_android.viewModel.PokemonViewModel
import br.com.lj.pokedex_android.viewModel.PokemonViewModelFactory

class PokemonListActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    private val viewModel by lazy {
        ViewModelProvider(this, PokemonViewModelFactory())
            .get(PokemonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_pokemons)

        viewModel.pokemons.observe(this, Observer {
            loadRecyclerView(it)
        })
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAdapter(pokemons)
    }
}