package br.com.lj.pokedex_android.view.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.lj.pokedex_android.databinding.ActivityDetailsPokemonBinding
import br.com.lj.pokedex_android.utils.CommonUtlis
import com.bumptech.glide.Glide

class PokemonInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsPokemonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPokemonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initValues()
    }


    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun initValues() {
        val i = intent
        val urlImage = i.getStringExtra("image")
        val namePokemon = i.getStringExtra("name")
        val numberPokemon = i.getStringExtra("number")
        val type1 = i.getStringExtra("type1")
        val type2 = i.getStringExtra("type2")
        val listSkills = i.getStringArrayListExtra("ability")
        val height = i.getStringExtra("height")
        val weight = i.getStringExtra("weight")
        val stats = i.getIntegerArrayListExtra("statsList")
        val flavorSpecies = i.getStringExtra("flavorItemSpecie")
        val genusSpecies = i.getStringExtra("genus")
        val gender = i.getIntExtra("genderRate", 0)


        Glide.with(this).load(urlImage).into(binding.ivPokemon)
        binding.tvName.text = namePokemon
        binding.tvNumber.text = numberPokemon
        binding.tvType1.text = type1
        binding.txvHeightPokemon.text = height.toString().plus(" m")
        binding.txvWeigthPokemon.text = CommonUtlis.formatterKg(weight).plus(" Kg")
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
                CommonUtlis.changeColorTypePoKemon(
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
                    CommonUtlis.changeColorTypePoKemon(
                        type2.decapitalize()
                    )
                )
            )
        }

        binding.txvGenderPokemon.text = CommonUtlis.defineGender(gender)
        binding.txvDescriptionPokemon.text = flavorSpecies
        binding.txvCategoryPokemon.text = genusSpecies
    }
}
