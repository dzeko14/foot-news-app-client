package my.dzeko.footapp.view.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment

import my.dzeko.footapp.R
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.presenter.NewsPresenter
import my.dzeko.footapp.view.adapter.NewsAdapter
import my.dzeko.footapp.view.interfaces.NewsView
import javax.inject.Inject

class NewsFragment : DaggerFragment(), NewsView {

    @Inject lateinit var mPresenter: NewsPresenter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar

    private val mArgs: NewsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.subscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unsubscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
            .apply {
                findViews(this)
                initializeRecyclerView()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.requestNews(mArgs.newsId)
    }

    private fun initializeRecyclerView() {
        mRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun findViews(v: View) {
        mRecyclerView = v.findViewById(R.id.recycler_view)
        mProgressBar = v.findViewById(R.id.progress_bar)
    }

    override fun setNews(news: News) {
        mRecyclerView.adapter = NewsAdapter(news, ::onOriginalUrlClickListener)
    }

    private fun onOriginalUrlClickListener(url: String) {

    }

    override fun showLoading() {
        mProgressBar.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
    }

    override fun hideLoading() {
        mProgressBar.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
    }
}
