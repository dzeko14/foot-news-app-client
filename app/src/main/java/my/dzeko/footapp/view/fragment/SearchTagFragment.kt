package my.dzeko.footapp.view.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.database.Cursor
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search_tag.view.*
import my.dzeko.footapp.R
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.presenter.SearchTagPresenter
import my.dzeko.footapp.view.adapter.TagListAdapter
import my.dzeko.footapp.view.interfaces.SearchTagView
import javax.inject.Inject


class SearchTagFragment : DaggerFragment(), SearchTagView {

    @Inject lateinit var mPresenter: SearchTagPresenter

    private lateinit var mSearchView: SearchView
    private lateinit var mRecyclerView: RecyclerView
    private val mAdapter = TagListAdapter(::onTagSelected, ::onTagClicked)


    private fun onTagSelected(tag: Tag) {
        mPresenter.onTagSelectedClick(tag)
    }

    private fun onTagClicked(tag: Tag) {
        val direction = SearchTagFragmentDirections.actionSearchTagFragmentToTagedNewsListFragment(tag.id)
        NavHostFragment.findNavController(this).navigate(direction)
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
        return inflater.inflate(R.layout.fragment_search_tag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSearchView = view.tag_sv
        setupSearchView()

        mRecyclerView = view.recycler_view
        setupRecyclerView()

        mPresenter.onEmptyQuery()
    }

    private fun setupRecyclerView() {
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
    }

    private fun setupSearchView() {
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    mPresenter.onQueryTextSubmit("$query%")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    mPresenter.onQueryTextChanged(newText)
                }
                return true
            }
        })
    }


    private var mOldTagsLiveData: LiveData<PagedList<Tag>>? = null
    private val mLiveDataObserver = Observer<PagedList<Tag>>{
        mAdapter.submitList(it)
    }

    override fun setTagsList(tags: LiveData<PagedList<Tag>>) {
        mOldTagsLiveData?.removeObserver(mLiveDataObserver)
        tags.observe(this, mLiveDataObserver)
        mOldTagsLiveData = tags
    }


    override fun setSearchSugestions(cursor: Cursor) {
        TODO("not implemented")
    }
}
