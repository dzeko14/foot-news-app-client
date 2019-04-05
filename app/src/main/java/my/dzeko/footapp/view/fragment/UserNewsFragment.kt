package my.dzeko.footapp.view.fragment


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import my.dzeko.footapp.R
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.presenter.UserNewsPresenter
import my.dzeko.footapp.view.adapter.NewsListAdapter
import my.dzeko.footapp.view.interfaces.UserNewsView
import javax.inject.Inject


class UserNewsFragment : DaggerFragment(), UserNewsView {

    @Inject lateinit var mPresenter: UserNewsPresenter

    private lateinit var mRecyclerView: RecyclerView
    private val mAdapter = NewsListAdapter(::onNewsItemClicked)

    private lateinit var mEmptyView: View

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
        return inflater.inflate(R.layout.fragment_user_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mEmptyView = view.findViewById(R.id.empty_view)

        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
        mPresenter.requestNewsList()
    }

    override fun setNewsList(newsList: LiveData<PagedList<NewsSummary>>) {
        newsList.observe(this, Observer {
            mAdapter.submitList(it)
            it?.let { mPresenter.onNewsListSizeCheck(it.size) }
        })
    }

    override fun navigateToNewsFragment(id: Long) {
        val action = UserNewsFragmentDirections.actionUserNewsListFragmentToNewsFragment(id)
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
}
