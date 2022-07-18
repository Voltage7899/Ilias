package com.company.ilias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.ilias.databinding.ElRecBinding
import com.squareup.picasso.Picasso


class FootballAdapter(val clickListener: ClickListener) : RecyclerView.Adapter<FootballAdapter.RaceViewHolder>() {

    private var raceListInAdapter= ArrayList<mMatch>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballAdapter.RaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.el_rec,parent,false)

        return RaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: FootballAdapter.RaceViewHolder, position: Int) {
        holder.bind(raceListInAdapter[position],clickListener)
    }

    override fun getItemCount(): Int {
        return raceListInAdapter.size
    }

    class RaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElRecBinding.bind(itemView)
        fun bind(match: mMatch, clickListener: ClickListener){
            binding.elRirstTeam.text=match.firstTeam
            binding.elSecondTeam.text=match.secondTeam
            binding.elScore.text=match.score
            Picasso.get().load(match.firstImage).fit().into(binding.elementImage1)
            Picasso.get().load(match.secondImage).fit().into(binding.elementImage2)
            itemView.setOnClickListener{

                clickListener.onClick(match)
            }

        }
    }
    fun loadListToAdapter(productList:ArrayList<mMatch>){
        raceListInAdapter= productList
        notifyDataSetChanged()
    }

    interface ClickListener{
        fun onClick(product: mMatch){

        }
    }
    fun deleteItem(i:Int):Int?{
        var id=raceListInAdapter.get(i).id

        raceListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }

}