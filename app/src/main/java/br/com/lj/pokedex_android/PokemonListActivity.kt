package br.com.lj.pokedex_android

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lj.pokedex_android.databinding.ActivityMainBinding
import br.com.lj.pokedex_android.domain.Pokemon
import br.com.lj.pokedex_android.view.PokemonAdapter
import br.com.lj.pokedex_android.viewModel.PokemonViewModel
import br.com.lj.pokedex_android.viewModel.PokemonViewModelFactory
import java.util.*

class PokemonListActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    private var isLoading = false
    private var totalPage = 1
    private var page = 1


    private val viewModel by lazy {
        ViewModelProvider(this, PokemonViewModelFactory())
            .get(PokemonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView = binding.rvPokemons

        viewModel.pokemons.observe(this) {
            loadRecyclerView(it)
            searchPokemons(it)
        }
    }

    private fun searchPokemons(pokemons: List<Pokemon?>) {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredContacts =
                    pokemons.filter { newText!!.uppercase(Locale.getDefault()) in it!!.name.uppercase(
                        Locale.getDefault()
                    ) }

                recyclerView.adapter = PokemonAdapter(filteredContacts)

                return false
            }
        })
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAdapter(pokemons)
    }
}