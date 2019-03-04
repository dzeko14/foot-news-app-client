package my.dzeko.footapp.view.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import my.dzeko.footapp.R
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.presenter.NewsListPresenter
import my.dzeko.footapp.view.adapter.NewsListAdapter
import my.dzeko.footapp.view.interfaces.NewsListView
import javax.inject.Inject

class NewsListFragment : DaggerFragment(), NewsListView {

    @Inject lateinit var mPresenter: NewsListPresenter

    private lateinit var mRecyclerView: RecyclerView
    private val mAdapter = NewsListAdapter()

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
        return inflater.inflate(R.layout.fragment_news_list, container, false)
            .also {v ->
                mRecyclerView = v.findViewById(R.id.recycler_view)
                mRecyclerView.layoutManager = LinearLayoutManager(context)
                mRecyclerView.adapter = mAdapter
                mPresenter.requestNewsList()
        }
    }

    override fun setNewsList(newsList: LiveData<PagedList<NewsSummary>>) {
        newsList.observe(this, Observer { mAdapter.submitList(it) })
    }
}
