package br.com.lj.pokedex_android.view.main.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.lj.pokedex_android.view.main.PokemonViewModel

@Suppress("UNCHECKED_CAST")
class PokemonViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PokemonViewModel() as T
    }
}