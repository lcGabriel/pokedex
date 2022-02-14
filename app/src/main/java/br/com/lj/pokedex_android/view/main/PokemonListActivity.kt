package br.com.lj.pokedex_android.view.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lj.pokedex_android.databinding.ActivityMainBinding
import br.com.lj.pokedex_android.domain.Pokemon
import br.com.lj.pokedex_android.domain.PokemonSpecies
import br.com.lj.pokedex_android.view.main.adapter.PokemonAdapter
import br.com.lj.pokedex_android.viewModel.main.PokemonViewModel
import br.com.lj.pokedex_android.viewModel.main.PokemonViewModelFactory
import java.util.*

class PokemonListActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    private var isScrolling = false
    private var isReload = false
    private var listPokemonInsert = ArrayList<Pokemon>()
    private var listPokemonSpeciesInsert = ArrayList<PokemonSpecies>()
    private var listPokemon: ArrayList<Pokemon?>? = arrayListOf()
    private var listPokemonName: ArrayList<String>? = arrayListOf()
    private var currentPage: Int = 0
    private var limit: Int = 10
    private var isFilter = false
    private var activity: PokemonListActivity? = null

    lateinit var layoutManager: LinearLayoutManager


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
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        viewModel.pokemons.observe(this) { pokemon ->
            viewModel.pokemonsSpecies.observe(this){ pokemonSpecies ->
                searchPokemons(pokemon, pokemonSpecies)
                makeListPokemon(pokemon, pokemonSpecies)
                addListPokemonFull(pokemon, pokemonSpecies)
            }
        }

        buildRecyclerView()
    }

    private fun buildRecyclerView() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val total = recyclerView.adapter?.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if(!isFilter) {
                    binding.progressBar.visibility = View.VISIBLE
                    if (total == lastVisibleItemPosition + 1 && newState == SCROLL_STATE_IDLE) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (isScrolling && (total == lastVisibleItemPosition + 1)) {
                                isScrolling = false
                                makeListPokemon(listPokemonInsert, listPokemonSpeciesInsert)
                            }
                        }, 8000L)
                    }
                }

                isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.progressBar.visibility = View.GONE
                isScrolling = false
            }
        })
    }

    private fun searchPokemons(pokemons: List<Pokemon?>, pokemonsSpecies: List<PokemonSpecies?>) {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                isFilter = false
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredContacts =
                    pokemons.filter {
                        newText!!.uppercase(Locale.getDefault()) in it!!.name.uppercase(
                            Locale.getDefault()
                        )
                    }

                isFilter = true
                recyclerView.adapter = PokemonAdapter(filteredContacts, activity!!, pokemonsSpecies)

                return false
            }
        })
    }

    private fun makeListPokemon(pokemons: List<Pokemon?>, pokemonSpecies: List<PokemonSpecies?>) {
        for (poke in pokemons) {
            if (isReload) {
                if (viewModel.checkPokemonName(listPokemonName!!.toList(), poke!!)) {
                    when (currentPage) {
                        limit -> {
                            loadRecyclerView(viewModel.reloadList(limit ,viewModel.listNamePokemon), pokemonSpecies)
                            break
                        }
                        else -> {
                            viewModel.listNamePokemon.add(poke.name)
                            listPokemon?.add(poke)
                            currentPage++
                        }
                    }
                }
            } else {
                when (currentPage) {
                    limit -> {
                        loadRecyclerView(listPokemon!!, pokemonSpecies)
                        binding.progressBar.visibility = View.GONE
                        isReload = true
                        break
                    }
                    else -> {
                        listPokemonName?.add(poke!!.name)
                        listPokemon?.add(poke)
                        currentPage++
                    }
                }

            }
        }
    }

    private fun addListPokemonFull(pokemons: List<Pokemon?>, pokemonsSpecies: List<PokemonSpecies?>) {
        for (poke in pokemons) {
            if (poke != null) {
                listPokemonInsert.add(poke)
            }
        }

        for (species in pokemonsSpecies){
            if (species != null) {
                listPokemonSpeciesInsert.add(species)
            }
        }
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>, pokemonsSpecies: List<PokemonSpecies?>) {
        recyclerView.adapter = PokemonAdapter(pokemons, this, pokemonsSpecies)
        activity = this
        currentPage - 10
    }
}