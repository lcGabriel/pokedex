package br.com.lj.pokedex_android.viewModel.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonDetailsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PokemonDetailsViewModel() as T
    }
}