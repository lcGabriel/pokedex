package br.com.lj.pokedex_android

import android.os.Bundle
import android.util.Log
import android.view.View
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
    private var lastPage: Boolean = false
    private var loading: Boolean = false
    private var initializeStateBottom: Boolean = false
    private var paginationPerPhase: Boolean = true
    private var isPlural: Boolean = false
    private var currentPage: Int = 1
    private var limit: Int = 10

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
            loadRecyclerView(it)
            searchPokemons(it)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = recyclerView.adapter?.itemCount

                    if(!isLoading){
                        if((visibleItemCount + pastVisibleItem) >= total!!){
                            page++
                            getPage()
                        }

                    }
                }
            }
        })
    }

    fun getPage(){
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
        val start = (page - 1) * limit
        val end =  (page) * limit

        for(i in start..end){
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

    fun getPokemons(isOnRefresh: Boolean){



    }


    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        recyclerView.adapter = PokemonAdapter(pokemons)
    }
}