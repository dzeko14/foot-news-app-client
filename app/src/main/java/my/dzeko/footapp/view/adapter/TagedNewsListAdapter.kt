package my.dzeko.footapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.news_list_tag_item.view.*
import my.dzeko.footapp.R
import my.dzeko.footapp.model.entity.NewsSummary
import my.dzeko.footapp.model.entity.Tag

private const val TAG_VIEW = 463

class TagedNewsListAdapter(private val tagClickListener: (Tag) -> Unit,
                           newsItemClickListener: (NewsSummary) -> Unit)
    : NewsListAdapter(newsItemClickListener) {

    var tag: Tag? = null

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): NewsListAdapter.AbstractNewsViewHolder {
        return when(type) {
            TAG_VIEW -> {
                val inflater = LayoutInflater.from(parent.context)
                TagVH(
                    inflater.inflate(R.layout.news_list_tag_item, parent, false),
                    tagClickListener
                )
            }

            else -> super.onCreateViewHolder(parent, type)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun onBindViewHolder(vieHolder: AbstractNewsViewHolder, position: Int) {
        when(position) {
            0 -> (vieHolder as TagVH).update(tag)

            else -> super.onBindViewHolder(vieHolder, position - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> TAG_VIEW

            else -> super.getItemViewType(position)
        }
    }

    private class TagVH(v: View, private val tagClickListener: (Tag) -> Unit)
        : NewsListAdapter.AbstractNewsViewHolder(v) {
        private val subscribeButton = v.subscribe_btn
        private val tagNameTV = v.tag_name_tv
        private val subscribeButtonTextArray = v.context
            .resources
            .getTextArray(R.array.tag_button_string)

        override fun update(news: NewsSummary?) {
            //Empty
        }

        fun update(tag: Tag?) {
            tagNameTV.text = tag?.name ?: ""
            tag?.let {
                subscribeButton.setOnClickListener {
                    tagClickListener(tag)
                    tag.isSelected = !tag.isSelected
                    subscribeButton.text = getButtonText(tag.isSelected)
                }
                subscribeButton.text = getButtonText(tag.isSelected)
            }
        }

        private fun getButtonText(isSelected: Boolean): CharSequence {
            return if (isSelected) subscribeButtonTextArray[1]
            else subscribeButtonTextArray[0]
        }
    }
}