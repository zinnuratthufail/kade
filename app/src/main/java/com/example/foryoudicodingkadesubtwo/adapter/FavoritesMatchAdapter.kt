package com.example.foryoudicodingkadesubtwo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foryoudicodingkadesubtwo.view.model.MatchFavoriteEntity
import com.example.foryoudicodingkadesubtwo.R
import kotlinx.android.synthetic.main.item_listmatch.view.*


class FavoritesMatchAdapter(
    private val favorite: List<MatchFavoriteEntity>,
    private val listener: (MatchFavoriteEntity) -> Unit
) : RecyclerView.Adapter<FavoritesMatchAdapter.FavoriteViewHolder>() {

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


        fun bindItem(favorite: MatchFavoriteEntity, listener: (MatchFavoriteEntity) -> Unit) {

            itemView.listTeamAwway.text = favorite.homeTeamName
            itemView.listTeamHome.text = favorite.awayTeamName
            itemView.away_score.text = favorite.awayScore
            itemView.home_score.text = favorite.homeScore
            itemView.title_time.text = favorite.matchDate + " " + favorite.matchTime
            itemView.setOnClickListener { listener(favorite) }

        }
    }
}