package br.com.lj.pokedex_android.view.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.lj.pokedex_android.databinding.ActivityDetailsPokemonBinding
import br.com.lj.pokedex_android.domain.PokemonSpecies
import br.com.lj.pokedex_android.utils.CommonUtlis
import br.com.lj.pokedex_android.viewModel.details.PokemonDetailsViewModel
import br.com.lj.pokedex_android.viewModel.details.PokemonDetailsViewModelFactory
import br.com.lj.pokedex_android.viewModel.main.PokemonViewModel
import br.com.lj.pokedex_android.viewModel.main.PokemonViewModelFactory
import com.bumptech.glide.Glide

class PokemonInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsPokemonBinding
    lateinit var layoutManager: LinearLayoutManager


    private val viewModel by lazy {
        ViewModelProvider(this, PokemonDetailsViewModelFactory())
            .get(PokemonDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPokemonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.pokemonsSpecies.observe(this) {
            initValues(it)
        }
    }


    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun initValues(listSpecies: List<PokemonSpecies?>) {
        val i = intent
        val urlImage = i.getStringExtra("image")
        val namePokemon = i.getStringExtra("name")
        val numberPokemon = i.getStringExtra("number")
        val type1 = i.getStringExtra("type1")
        val type2 = i.getStringExtra("type2")
        val listSkills = i.getStringArrayListExtra("ability")
        val height = i.getDoubleExtra("height", 0.0)
        val weight = i.getDoubleExtra("weight", 0.0)
        val stats = i.getIntegerArrayListExtra("statsList")


        Glide.with(this).load(urlImage).into(binding.ivPokemon)
        binding.tvName.text = namePokemon
        binding.tvNumber.text = numberPokemon
        binding.tvType1.text = type1
        binding.txvHeightPokemon.text = height.toString().plus("m")
        binding.txvWeigthPokemon.text = weight.toString().plus("Kg")
        binding.txvSkillsPokemon.text = listSkills!![0].capitalize()
        binding.customItem.txvHpPokemon.text = stats!![0].toString().plus(" %")
        binding.customItem.txvAttackPokemon.text = stats[1].toString().plus(" %")
        binding.customItem.txvDefencePokemon.text = stats[2].toString().plus(" %")
        binding.customItem.txvSpecialAttackPokemon.text = stats[3].toString().plus(" %")
        binding.customItem.txvSpeedPokemon.text = stats[4].toString().plus(" %")
        binding.customItem.txvSpecialDefencePokemon.text = stats[5].toString().plus(" %")
        binding.customItem.txvTotalPokemon.text =
            (stats[0] + stats[1] + stats[2] + stats[3] + stats[4]).toString().plus(" %")


        binding.tvType1.setBackgroundColor(
            Color.parseColor(
                CommonUtlis.CommonUtils.changeColorTypePoKemon(
                    type1!!.decapitalize()
                )
            )
        )

        binding.tvType2.visibility = View.GONE
        if (!type2.isNullOrEmpty()) {
            binding.tvType2.text = type2
            binding.tvType2.visibility = View.VISIBLE

            binding.tvType2.setBackgroundColor(
                Color.parseColor(
                    CommonUtlis.CommonUtils.changeColorTypePoKemon(
                        type2.decapitalize()
                    )
                )
            )
        }

        for (species in listSpecies) {
            if (species != null) {
                binding.txvDescriptionPokemon.text = species.flavor_text_entries[7].flavor_text
                binding.txvCategoryPokemon.text = species.genera[4].genus
            }
        }
    }
}
