package my.dzeko.footapp.view.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import my.dzeko.footapp.R
import my.dzeko.footapp.extension.formattedTime
import my.dzeko.footapp.model.entity.News
import java.lang.Exception
import java.util.*

private const val HEAD = 0
private const val BOTTOM = 1
private const val CONTENT = 2
private const val IMAGE = 3

class NewsAdapter (
    private val news: News,
    private val onOriginalUrlClick: (String) -> Unit,
    private val onTagClick: (Long) -> Unit
) : RecyclerView.Adapter<BasicNewsVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicNewsVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            HEAD -> {
                val view = layoutInflater
                    .inflate(R.layout.news_head, parent, false)
                HeadVH(view)
            }

            BOTTOM -> {
                val view = layoutInflater
                    .inflate(R.layout.news_bottom, parent, false)
                BottomVH(view, onOriginalUrlClick, onTagClick)
            }

            CONTENT -> {
                val view = layoutInflater
                    .inflate(R.layout.news_content, parent, false)
                ContentVH(view)
            }

            IMAGE -> {
                val view = layoutInflater
                    .inflate(R.layout.news_image, parent, false)
                ImageVH(view)
            }

            else -> throw Exception("Wrong view type in NewsAdapter")
        }
    }


    override fun getItemCount(): Int {
        return news.content.size + 2 + news.images.size
    }

    override fun getItemViewType(position: Int): Int {
       return when {
           position == 0 -> HEAD
           position == (itemCount - 1) -> BOTTOM
           position % 2 == 1 -> CONTENT
           else -> IMAGE
       }
    }

    override fun onBindViewHolder(viewHolder: BasicNewsVH, position: Int)
        = viewHolder.update(news, position)

    private class HeadVH(v: View) : BasicNewsVH(v) {
        private val titleTV = v.findViewById<TextView>(R.id.title_tv)
        private val dateTV = v.findViewById<TextView>(R.id.date_tv)


        override fun update(news: News, position: Int) {
            titleTV.text = news.title
            dateTV.text = Date(news.date).formattedTime()
        }
    }

    private class ContentVH(v: View) : BasicNewsVH(v) {
        private val contentTV = v.findViewById<TextView>(R.id.content_tv)

        override fun update(news: News, position: Int) {
            contentTV.text = if (mContentPosition.containsKey(position)) {
                 news.content[mContentPosition[position]!!]
            } else {
                mContentPosition[position] = mCurrentContentIndex
                news.content[mCurrentContentIndex++]
            }
        }

        companion object {
            @JvmStatic
            private val mContentPosition = mutableMapOf<Int, Int>()
            @JvmStatic
            private var mCurrentContentIndex = 0
        }
    }

    private class BottomVH(v: View, private val onOriginalUrlClick: (String) -> Unit,
                           private val onTagClick: (Long) -> Unit)
        : BasicNewsVH(v) {
        private val tagsFL = v.findViewById<FlexboxLayout>(R.id.tags_fl)
        private val originalButton = v.findViewById<Button>(R.id.original_url_btn)
        private val layoutInflater = LayoutInflater.from(v.context)

        override fun update(news: News, position: Int) {
            originalButton.setOnClickListener { onOriginalUrlClick(news.originalUrl) }
            news.tags?.let { tags ->
                for (tag in tags) {
                    val button = layoutInflater
                        .inflate(R.layout.news_tag_button,
                            tagsFL as ViewGroup,
                            false) as Button
                    button.text = tag.name
                    button.setOnClickListener { onTagClick(tag.id) }
                    tagsFL.addView(button)
                }
            }
        }
    }

    private class ImageVH(v: View) : BasicNewsVH(v) {
        private val mNewsImageView = v.findViewById<ImageView>(R.id.news_iv)

        override fun update(news: News, position: Int) {
            val imageStringUrl = if (mImagesPosition.containsKey(position)) {
                news.images[mImagesPosition[position]!!]
            } else {
                mImagesPosition[position] = mCurrentContentIndex
                news.images[mCurrentContentIndex++]
            }
            val imageUri = Uri.parse(imageStringUrl)
            Glide.with(mNewsImageView.context).load(imageUri).into(mNewsImageView)
        }

        companion object {
            @JvmStatic
            private val mImagesPosition = mutableMapOf<Int, Int>()
            @JvmStatic
            private var mCurrentContentIndex = 0
        }
    }
}

abstract class BasicNewsVH(v: View) : RecyclerView.ViewHolder(v) {
    abstract fun update(news: News, position: Int)
}