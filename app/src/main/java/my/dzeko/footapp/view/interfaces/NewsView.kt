package my.dzeko.footapp.view.interfaces

import my.dzeko.footapp.model.entity.News

interface NewsView {
    fun setNews(news: News)
    fun showLoading()
    fun hideLoading()
}