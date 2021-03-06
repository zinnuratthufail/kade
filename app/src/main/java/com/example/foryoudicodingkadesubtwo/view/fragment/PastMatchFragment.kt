package com.example.foryoudicodingkadesubtwo.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foryoudicodingkadesubtwo.PastMatch.PastMatchPresenter
import com.example.foryoudicodingkadesubtwo.PastMatch.PastMatchView
import com.example.foryoudicodingkadesubtwo.R
import com.example.foryoudicodingkadesubtwo.adapter.PastMatchAdapter
import com.example.foryoudicodingkadesubtwo.helper.ApiRepository
import com.example.foryoudicodingkadesubtwo.helper.setIDleague
import com.example.foryoudicodingkadesubtwo.view.activity.DetailMatchPastActivity
import com.example.foryoudicodingkadesubtwo.view.model.PastMatchInit
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_listcontent.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class PastMatchFragment : Fragment(), PastMatchView {
    private lateinit var mAdapter: PastMatchAdapter
    private var teams: MutableList<PastMatchInit> = mutableListOf()
    private lateinit var presenter: PastMatchPresenter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setPresent()
        setRecycler()

    }

    fun setRecycler() {
        mAdapter = PastMatchAdapter(teams) {
            startActivity<DetailMatchPastActivity>(DetailMatchPastActivity.SET_PARCELABLE to it)
        }
        mAdapter.notifyDataSetChanged()

        recyclerViewMain.layoutManager = LinearLayoutManager(context)
        recyclerViewMain.adapter = mAdapter

    }

    fun setPresent() {

        val request = ApiRepository()
        val gson = Gson()
        presenter = PastMatchPresenter(this, request, gson)

        presenter.getPastMatchList(context?.let { setIDleague.getToken(it) })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listcontent, container, false)

    }


    override fun showLoading() {
//        progressBar.visible()
    }

    override fun hideLoading() {
//        progressBar.invisible()
    }

    override fun showTeamList(data: List<PastMatchInit>) {

        if (data.size == null) {
            toast("data kosong")
        } else {
            teams.clear()

            Log.d("jsonres", data.toString())

            teams.addAll(data)
            mAdapter.notifyDataSetChanged()
        }
    }


}