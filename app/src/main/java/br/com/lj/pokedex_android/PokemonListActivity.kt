package br.com.lj.pokedex_android

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
import br.com.lj.pokedex_android.view.adapter.PokemonAdapter
import br.com.lj.pokedex_android.viewModel.PokemonViewModel
import br.com.lj.pokedex_android.viewModel.PokemonViewModelFactory
import java.util.*

class PokemonListActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    private var isScrolling = false
    private var isReload = false
    private var listPokemonInsert = ArrayList<Pokemon>()
    private var listPokemon: ArrayList<Pokemon?>? = arrayListOf()
    private var listPokemonName: ArrayList<String>? = arrayListOf()
    private var currentPage: Int = 0
    private var limit: Int = 10
    private var isFilter = false

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

        viewModel.pokemons.observe(this) {
            searchPokemons(it)
            makeListPokemon(it)
            addListPokemonFull(it)
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
                                makeListPokemon(listPokemonInsert)
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

    private fun searchPokemons(pokemons: List<Pokemon?>) {
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
                recyclerView.adapter = PokemonAdapter(filteredContacts)

                return false
            }
        })
    }

    private fun makeListPokemon(pokemons: List<Pokemon?>) {
        for (poke in pokemons) {
            if (isReload) {
                if (viewModel.checkPokemonName(listPokemonName!!.toList(), poke!!)) {
                    when (currentPage) {
                        limit -> {
                            loadRecyclerView(viewModel.reloadList(limit ,viewModel.listNamePokemon))
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
                        loadRecyclerView(listPokemon!!)
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

    private fun addListPokemonFull(pokemons: List<Pokemon?>) {
        for (poke in pokemons) {
            if (poke != null) {
                listPokemonInsert.add(poke)
            }
        }
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        recyclerView.adapter = PokemonAdapter(pokemons)
        currentPage - 10
    }
}