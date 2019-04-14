package my.dzeko.footapp.view.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
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
    private val mAdapter = NewsListAdapter(::onNewsItemClicked)

    private lateinit var mEmptyView: View
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private fun onNewsItemClicked(newsSummary: NewsSummary) {
        mPresenter.onNewsItemClicked(newsSummary)
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        mEmptyView = view.findViewById(R.id.empty_view)

        //setup swipe refresh layout
        mSwipeRefreshLayout.setOnRefreshListener { mPresenter.onNewsListUpdate() }
        val swipeRefreshColor = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
        mSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColor)
    }

    override fun setNewsList(newsList: LiveData<PagedList<NewsSummary>>) {
        newsList.observe(this, Observer {
            mAdapter.submitList(it)
            it?.let {
                mPresenter.onNewsListSizeCheck(it.size)
            }
        })
    }

    override fun navigateToNewsFragment(id: Long) {
        val action = NewsListFragmentDirections.actionNewsListFragmentToNewsFragment(id)
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun showEmptyScreen() {
        mEmptyView.visibility = View.VISIBLE
        mRecyclerView.visibility = View.INVISIBLE
    }

    override fun hideEmptyScreen() {
        mEmptyView.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mSwipeRefreshLayout.isRefreshing = true
        mRecyclerView.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        mSwipeRefreshLayout.isRefreshing = false
        mRecyclerView.visibility = View.VISIBLE
    }

    override fun showItemsUpdated() {
        Snackbar
            .make(view!!, R.string.items_updated, Snackbar.LENGTH_LONG)
            .setAction(R.string.go_to_it) {
                mRecyclerView.smoothScrollToPosition(0)
            }.show()
    }
}
