package com.savvy.domain.news

import com.savvy.data.base.repository.NewsRepository
import com.savvy.domain.news.mock.LoadNewsMock.loadListNewsResponse
import com.savvy.domain.usecase.news.LoadListNewsUseCase
import io.github.serpro69.kfaker.Faker
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.mockito.Mock
import kotlin.test.BeforeTest
import kotlin.test.Test

class NewsListTest {
    private lateinit var faker: Faker

    @MockK
    lateinit var newsRepository: NewsRepository

    @BeforeTest
    fun setUp(){
        MockKAnnotations.init(this)
        faker = Faker()
    }

    @Test
    fun loadListNewsWhenSuccess(){
        val result = loadListNewsResponse(faker)

        every {
            newsRepository.getNewsList(any(), any(), any(), any(), any())
        }.returns(
            Single.just(
                result
            )
        )

        val usecase = LoadListNewsUseCase(newsRepository)
        usecase.execute("", "us", 1, 1, "").test().assertNoErrors().assertValue(result)
    }
}