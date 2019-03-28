package my.dzeko.footapp.view.fragment


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_taged_news_list.view.*
import kotlinx.android.synthetic.main.news_list_tag_item.view.*
import my.dzeko.footapp.R
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.Tag
import my.dzeko.footapp.presenter.TagedNewsListPresenter
import my.dzeko.footapp.view.adapter.NewsListAdapter
import my.dzeko.footapp.view.interfaces.TaggedNewsListView
import javax.inject.Inject


class TaggedNewsListFragment : DaggerFragment(), TaggedNewsListView {

    @Inject lateinit var mPresenter: TagedNewsListPresenter

    private lateinit var mRecyclerView: RecyclerView
    private val mAdapter = NewsListAdapter(::onNewsItemClicked)

    private val mArgs: TaggedNewsListFragmentArgs by navArgs()

    private lateinit var mTagTV: TextView
    private lateinit var mSubscribeBtn: Button
    private lateinit var mToolBar: Toolbar
    private lateinit var mCollapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var mAppBarLayout: AppBarLayout

    private var mTag: Tag? = null

    override val subscribeButtonTextArray: Array<CharSequence>
            by lazy { resources.getTextArray(R.array.tag_button_string) }

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

        //Initialize recycler view
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter

        //Find tool bar, collapsingToolbarLayout, tag text view and button
        mToolBar = view.findViewById(R.id.toolbar)
        mTagTV = view.tag_name_tv
        mSubscribeBtn = view.subscribe_btn
        mCollapsingToolbarLayout = view.collapsing_toolbar
        mAppBarLayout = view.app_bar_layout

        mSubscribeBtn.setOnClickListener {
            mPresenter.onSubscribeButtonClick(mTag)
        }

        //Initialize toolbar with navigation controller
        val navController = NavHostFragment.findNavController(this)
        mToolBar.setupWithNavController(navController)

        setupAppToolBar()

        mPresenter.requestNewsList(mArgs.tagId)
    }

    private fun setupAppToolBar() {
        mAppBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            mPresenter.onAppBarOffsetChanged(appBarLayout.totalScrollRange, verticalOffset)
        })
    }

    override fun setSubscribeButtonText(text: CharSequence) {
        mSubscribeBtn.text = text
    }

    override fun setNewsListAndTag(newsList: LiveData<PagedList<NewsSummary>>, tag: Tag) {
        newsList.observe(this, Observer { mAdapter.submitList(it) })
        mTagTV.text = tag.name
        mTag = tag
    }

    override fun navigateToNewsFragment(id: Long) {
        val action = TaggedNewsListFragmentDirections.actionTagedNewsListFragmentToNewsFragment(id)
        NavHostFragment.findNavController(this).navigate(action)
    }


    override fun showTitle() {
        mCollapsingToolbarLayout.title = mTag?.name ?: " "
    }

    override fun hideTitle() {
        mCollapsingToolbarLayout.title = " "
    }
}
