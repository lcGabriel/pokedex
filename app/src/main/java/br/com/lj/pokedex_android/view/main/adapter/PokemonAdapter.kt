package br.com.lj.pokedex_android.view.main.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.lj.pokedex_android.R
import br.com.lj.pokedex_android.domain.Pokemon
import br.com.lj.pokedex_android.utils.CommonUtlis
import br.com.lj.pokedex_android.view.details.PokemonInfoActivity
import br.com.lj.pokedex_android.view.main.PokemonListActivity
import com.bumptech.glide.Glide

class PokemonAdapter(
    private val items: List<Pokemon?>,
    private val activity: PokemonListActivity
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val activity = activity

        holder.bindView(item,activity)
    }

    override fun getItemCount() = items.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Pokemon?, activity: PokemonListActivity) = with(itemView) {
            val ivPokemon = findViewById<ImageView>(R.id.iv_pokemon)
            val tvNumber = findViewById<TextView>(R.id.tv_number)
            val tvName = findViewById<TextView>(R.id.tv_name)
            val tvType1 = findViewById<TextView>(R.id.tv_type_1)
            val tvType2 = findViewById<TextView>(R.id.tv_type_2)
            val ablt = ArrayList<String>()
            val statsList = ArrayList<Int>()
            val card = findViewById<ConstraintLayout>(R.id.card_click)

            item?.let {
                Glide.with(itemView.context).load(it.imageUrl).into(ivPokemon)

                tvNumber.text = "N° ${item.formatterNumber}"
                tvName.text = item.formatterName
                tvType1.text = item.types[0].name.capitalize()
                tvType1.setBackgroundColor(Color.parseColor(CommonUtlis.CommonUtils.changeColorTypePoKemon(
                    item.types[0].name
                )))

                if (item.types.size > 1) {
                    tvType2.visibility = View.VISIBLE
                    tvType2.text = item.types[1].name.capitalize()
                    tvType2.setBackgroundColor(Color.parseColor(CommonUtlis.CommonUtils.changeColorTypePoKemon(
                        item.types[1].name
                    )))
                } else {
                    tvType2.visibility = View.GONE
                }

                for(ability in item.abilities){
                    ablt.add(ability.name)
                }

                for(stats in item.stats){
                    statsList.add(stats.base_stat)
                }

                card.setOnClickListener {
                    Intent(activity, PokemonInfoActivity::class.java).apply {
                        putExtra("name", tvName.text)
                        putExtra("number",tvNumber.text)
                        putExtra("image", item.imageUrl)
                        putExtra("type1", tvType1.text)
                        putExtra("type2", tvType2.text)
                        putExtra("weight", item.weight.toDouble())
                        putExtra("height", item.height.toDouble())
                        putExtra("ability", ablt)
                        putExtra("statsList", statsList)

                        activity.startActivity(this)
                    }
                }
            }
        }
    }


}