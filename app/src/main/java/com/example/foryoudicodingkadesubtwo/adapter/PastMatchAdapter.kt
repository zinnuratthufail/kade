package com.example.foryoudicodingkadesubtwo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foryoudicodingkadesubtwo.R
import com.example.foryoudicodingkadesubtwo.view.model.NextMatchInit
import com.example.foryoudicodingkadesubtwo.view.model.PastMatchInit
import kotlinx.android.synthetic.main.item_listmatch.view.*

class PastMatchAdapter (private val favorite: List<PastMatchInit>,
                        private val listener: (PastMatchInit) -> Unit

) : RecyclerView.Adapter<PastMatchAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_listmatch, parent, false)
        return FavoriteViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size


    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindItem(match: PastMatchInit, listener: (PastMatchInit) -> Unit) {

            itemView.listTeamAwway.text = match.strHomeTeam
            itemView.listTeamHome.text = match.strAwayTeam
            itemView.away_score.text = match.intAwayScore
            itemView.home_score.text = match.intHomeScore
            itemView.title_time.text = match.dateEvent+" "+match.strTimeLocal
            itemView.setOnClickListener { listener(match) }

        }
    }
}