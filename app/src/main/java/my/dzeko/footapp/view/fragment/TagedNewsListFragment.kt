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
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import my.dzeko.footapp.R
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.presenter.TagedNewsListPresenter
import my.dzeko.footapp.view.adapter.TagedNewsListAdapter
import my.dzeko.footapp.view.interfaces.TagedNewsListView
import javax.inject.Inject


class TagedNewsListFragment : DaggerFragment(), TagedNewsListView {

    @Inject lateinit var mPresenter: TagedNewsListPresenter

    private lateinit var mRecyclerView: RecyclerView
    private val mAdapter = TagedNewsListAdapter(::onTagClickListener, ::onNewsItemClicked)

    private val mArgs: TagedNewsListFragmentArgs by navArgs()

    private fun onTagClickListener(tag: Tag) {
        mPresenter.onTagClickListener(tag)
    }

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
        return inflater.inflate(R.layout.fragment_taged_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
        mPresenter.requestNewsList(mArgs.tagId)
    }

    override fun setNewsListAndTag(newsList: LiveData<PagedList<NewsSummary>>, tag: Tag) {
        newsList.observe(this, Observer { mAdapter.submitList(it) })
        mAdapter.tag = tag
    }

    override fun navigateToNewsFragment(id: Long) {
        val action = TagedNewsListFragmentDirections.actionTagedNewsListFragmentToNewsFragment(id)
        NavHostFragment.findNavController(this).navigate(action)
    }
}
