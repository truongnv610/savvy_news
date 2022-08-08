package com.savvy.domain.news.mock

import com.savvy.data.base.model.news.News
import com.savvy.data.base.model.news.NewsPagination
import io.github.serpro69.kfaker.Faker

object LoadNewsMock {

    fun loadListNewsResponse(faker: Faker): NewsPagination {
        val newsList = listItemNews(faker)
        return NewsPagination(
            totalResults = faker.random.nextInt(),
            status = "ok",
            articles = newsList
        )
    }

    private fun listItemNews(faker: Faker): List<News> {
        return (0..10).map { faker.randomProvider.randomClassInstance<News>() }.toList()
    }
}