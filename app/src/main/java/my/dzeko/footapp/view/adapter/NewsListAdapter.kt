package my.dzeko.footapp.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import my.dzeko.footapp.R
import my.dzeko.footapp.extension.formattedTime
import my.dzeko.footapp.model.entity.News
import my.dzeko.footapp.model.entity.NewsSummary

open class NewsListAdapter(
    private val mClickListener: (NewsSummary) -> Unit
)
    : PagedListAdapter<NewsSummary, NewsListAdapter.AbstractNewsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): AbstractNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_list_item, parent, false)
        return NewsViewHolder(view, mClickListener)
    }

    override fun onBindViewHolder(vieHolder: AbstractNewsViewHolder, position: Int)
            = vieHolder.update(getItem(position))

    private class NewsViewHolder(v: View, private val mClickListener: (NewsSummary) -> Unit)
        : AbstractNewsViewHolder(v) {
        private val mSummaryTV = v.findViewById<TextView>(R.id.summary_tv)
        private val mTitleTV = v.findViewById<TextView>(R.id.title_tv)
        private val mDateTV = v.findViewById<TextView>(R.id.date_tv)

        override fun update(news: NewsSummary?) {
            if (news == null) {
                mSummaryTV.text = " "
                mTitleTV.text = " "
                mDateTV.text = " "
                itemView.setOnClickListener(null)
            } else {
                mSummaryTV.text = news.summary
                mTitleTV.text = news.title
                mDateTV.text = news.date.formattedTime(itemView.resources)
                itemView.setOnClickListener { mClickListener(news) }
            }
        }
    }

    abstract class AbstractNewsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        abstract fun update(news: NewsSummary?)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsSummary>() {
            override fun areItemsTheSame(news1: NewsSummary, news2: NewsSummary): Boolean {
                return news1.id == news2.id
            }

            override fun areContentsTheSame(news1: NewsSummary, news2: NewsSummary): Boolean {
                return news1 == news2
            }

        }
    }
}