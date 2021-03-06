package com.example.foryoudicodingkadesubtwo.view.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.foryoudicodingkadesubtwo.helper.ApiRepository
import com.example.foryoudicodingkadesubtwo.view.model.MatchFavoriteEntity
import com.example.foryoudicodingkadesubtwo.ImageAway.ImageAwayPresenter
import com.example.foryoudicodingkadesubtwo.ImageAway.ImageAwayView
import com.example.foryoudicodingkadesubtwo.ImageHome.ImageHomePresenter
import com.example.foryoudicodingkadesubtwo.ImageHome.ImageHomeView
import com.example.foryoudicodingkadesubtwo.R
import com.example.foryoudicodingkadesubtwo.helper.database
import com.example.foryoudicodingkadesubtwo.view.model.ImageAwayInit
import com.example.foryoudicodingkadesubtwo.view.model.ImageHomeInit
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailmatch.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailMatchFavoritesActivity : AppCompatActivity(), ImageHomeView, ImageAwayView {

    private lateinit var presenter: ImageHomePresenter
    private lateinit var presenteraway: ImageAwayPresenter
    private lateinit var data: MatchFavoriteEntity
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private lateinit var id: String
    val request = ApiRepository()
    val gson = Gson()


    companion object {
        const val SET_PARCELABLE = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailmatch)
        initToolbar()
        data = intent.getParcelableExtra(SET_PARCELABLE) as MatchFavoriteEntity
        id = data.matchId
        home_team.text = data.homeTeamName
        away_team.text = data.awayTeamName
        scoreHome.text = data.homeScore
        scoreAway.text = data.awayScore
        title_time.text = data.matchDate + " " + data.matchTime
        Goalhome.text = data.Goalhome
        Goalaway.text = data.Goalaway
        GkHome.text = data.GkHome
        GkAway.text = data.GkAway
        DeffHome.text = data.DeffHome
        DeffAway.text = data.DeffAway
        forwardHome.text = data.forwardHome
        forwardAway.text = data.forwardAway
        MidHome.text = data.MidHome
        MidAway.text = data.MidAway
        subHome.text = data.subHome
        subAway.text = data.subAway
        redHome.text = data.redHome
        redAway.text = data.redAway
        yellowHome.text = data.yellowHome
        yellowAway.text = data.yellowAway

        favoriteState()
        presentHome()
        presentAway()

    }


    fun presentHome() {

        presenter = ImageHomePresenter(this, request, gson)

        presenter.getImageHome(data.homeTeamId)
    }


    fun presentAway() {
        presenteraway = ImageAwayPresenter(this, request, gson)

        presenteraway.getAwayImage(data.awayTeamId)

    }

    override fun showLoading() {
//        progressBar.visible()
    }

    override fun hideLoading() {
//        progressBar.invisible()
    }

    override fun showTeamList(data: List<ImageHomeInit>) {
        val team = data.get(0)

        team.strTeamBadge.let { Picasso.get().load(it).into(image_home) }

    }

    override fun showImageAway(data: List<ImageAwayInit>) {
        val team = data.get(0)

        team.strTeamBadge.let { Picasso.get().load(it).into(image_away) }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = " "
        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_heart_border)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    MatchFavoriteEntity.FAVORITE_MATCH,
                    MatchFavoriteEntity.MATCH_ID to data.matchId,
                    MatchFavoriteEntity.HOME_TEAM_ID to data.homeTeamId,
                    MatchFavoriteEntity.AWAY_TEAM_ID to data.awayTeamId,
                    MatchFavoriteEntity.HOME_TEAM_NAME to data.homeTeamName,
                    MatchFavoriteEntity.AWAY_TEAM_NAME to data.awayTeamName,
                    MatchFavoriteEntity.HOME_SCORE to data.homeScore,
                    MatchFavoriteEntity.AWAY_SCORE to data.awayScore,
                    MatchFavoriteEntity.MATCH_DATE to data.matchDate,
                    MatchFavoriteEntity.MATCH_TIME to data.matchTime,
                    MatchFavoriteEntity.GOAL_HOME to data.Goalhome,
                    MatchFavoriteEntity.GOAL_AWAY to data.Goalaway,
                    MatchFavoriteEntity.GK_HOME to data.GkHome,
                    MatchFavoriteEntity.GK_AWAY to data.GkAway,
                    MatchFavoriteEntity.DEFF_HOME to data.DeffHome,
                    MatchFavoriteEntity.DEFF_AWAY to data.DeffAway,
                    MatchFavoriteEntity.FORWARD_HOME to data.forwardHome,
                    MatchFavoriteEntity.FORWARD_AWAY to data.forwardAway,
                    MatchFavoriteEntity.MID_HOME to data.MidHome,
                    MatchFavoriteEntity.MID_AWAY to data.MidAway,
                    MatchFavoriteEntity.SUB_HOME to data.subHome,
                    MatchFavoriteEntity.SUB_AWAY to data.subAway,
                    MatchFavoriteEntity.RED_HOME to data.redHome,
                    MatchFavoriteEntity.RED_AWAY to data.redAway,
                    MatchFavoriteEntity.YELLOW_HOME to data.yellowHome,
                    MatchFavoriteEntity.YELLOW_AWAY to data.yellowAway


                )
            }


            toast("ditambahkan")

        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }


    }



    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    MatchFavoriteEntity.FAVORITE_MATCH,
                    "(${MatchFavoriteEntity.MATCH_ID} = {matchId})",
                    "matchId" to id
                )
            }
            toast("dihapus")
        } catch (e: SQLiteConstraintException) {
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(MatchFavoriteEntity.FAVORITE_MATCH)
                .whereArgs(
                    "(MATCH_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<MatchFavoriteEntity>())
            if (favorite.isNotEmpty()) {
                isFavorite = true
                toast("data, ${id}, sudah ada")
            }

        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite)
                    removeFromFavorite()
                else
                    addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}