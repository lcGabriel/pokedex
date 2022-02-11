package br.com.lj.pokedex_android.view.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.lj.pokedex_android.databinding.ActivityDetailsPokemonBinding
import br.com.lj.pokedex_android.utils.CommonUtlis
import br.com.lj.pokedex_android.viewModel.PokemonViewModel
import br.com.lj.pokedex_android.viewModel.PokemonViewModelFactory
import com.bumptech.glide.Glide

class PokemonInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsPokemonBinding
    lateinit var layoutManager: LinearLayoutManager


    private val viewModel by lazy {
        ViewModelProvider(this, PokemonViewModelFactory())
            .get(PokemonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPokemonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initValues()
    }


    @SuppressLint("SimpleDateFormat")
    private fun initValues() {
        val i = intent
        val urlImage = i.getStringExtra("image")
        val namePokemon = i.getStringExtra("name")
        val numberPokemon = i.getStringExtra("number")
        val type1 = i.getStringExtra("type1")
        val type2 = i.getStringExtra("type2")

        Glide.with(this).load(urlImage).into(binding.ivPokemon)
        binding.tvName.text = namePokemon
        binding.tvNumber.text = numberPokemon
        binding.tvType1.text = type1

        binding.tvType1.setBackgroundColor(
            Color.parseColor(
                CommonUtlis.CommonUtils.changeColorTypePoKemon(
                    type1!!
                )
            )
        )

        binding.tvType2.visibility = View.GONE
        if (!type2.isNullOrEmpty()) {
            binding.tvType2.text = type1
            binding.tvType2.visibility = View.VISIBLE

            binding.tvType1.setBackgroundColor(
                Color.parseColor(
                    CommonUtlis.CommonUtils.changeColorTypePoKemon(
                        type2
                    )
                )
            )
        }
    }

}
