package my.dzeko.footapp.view.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tags_list_item.view.*
import my.dzeko.footapp.R
import my.dzeko.footapp.model.entity.Tag

class TagListAdapter(
    private val tagSelectedCallback: (Tag) -> Unit,
    private val tagClickCallback: (Tag) -> Unit
) : PagedListAdapter<Tag, TagListAdapter.VH>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tags_list_item,
                parent,
                false
            ),
            tagSelectedCallback,
            tagClickCallback
        )
    }

    override fun onBindViewHolder(vh: VH, position: Int) {
        vh.update(
            getItem(position)
        )
    }

    class VH(v: View,
        private val tagSelectedCallback: (Tag) -> Unit,
        private val tagClickCallback: (Tag) -> Unit)
        : RecyclerView.ViewHolder(v) {

        private val nameRV = v.name_tv
        private val isSelectedButton = v.is_selected_btn

        fun update(tag: Tag?) {
            if (tag != null) {
                nameRV.text = tag.name
                isSelectedButton.apply {
                    isSelected = tag.isSelected
                    visibility = View.VISIBLE
                    setOnClickListener {
                        val state = !it.isSelected
                        it.isSelected = state
                        tag.isSelected = state
                        tagSelectedCallback(tag)
                    }
                }
                itemView.setOnClickListener { tagClickCallback(tag) }
            } else {
                nameRV.text = ""
                isSelectedButton.visibility = View.INVISIBLE
                itemView.setOnClickListener(emptyClickListener)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(p0: Tag, p1: Tag): Boolean {
                return p0.id == p1.id
            }

            override fun areContentsTheSame(p0: Tag, p1: Tag): Boolean {
                return p0 == p1
            }
        }

        private val emptyClickListener = View.OnClickListener { }
    }
}