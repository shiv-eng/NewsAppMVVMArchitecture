package com.example.newsapp.ui.pagination

import TestLogger
import androidx.paging.PagingSource
import com.example.newsapp.data.api.NetworkService
import com.example.newsapp.data.model.topheadlines.ApiArticle
import com.example.newsapp.data.model.topheadlines.TopHeadlinesResponse
import com.example.newsapp.utils.AppConstant
import com.example.newsapp.utils.DispatcherProvider
import com.example.newsapp.utils.NetworkHelper
import com.example.newsapp.utils.TestDispatcherProvider
import com.example.newsapp.utils.TestNetworkHelper
import com.example.newsapp.utils.logger.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsPagingSourceTest {

    @Mock
    private lateinit var networkService: NetworkService

    private lateinit var pagingSource: TopHeadlinePagingSource

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var networkHelper: NetworkHelper

    private lateinit var logger: Logger

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        networkHelper = TestNetworkHelper()
        logger = TestLogger()
        pagingSource = TopHeadlinePagingSource(networkService)
    }

    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            // Given
            val page = 1
            val articles = emptyList<ApiArticle>()

            val topHeadlinesResponse =
                TopHeadlinesResponse(status = "ok", totalResults = 0, apiArticles = articles)

            doReturn(topHeadlinesResponse)
                .`when`(networkService).getTopHeadlines(
                    country = AppConstant.COUNTRY,
                    page = page,
                    pageSize = AppConstant.PAGE_SIZE
                )

            // When
            val result = pagingSource.load(PagingSource.LoadParams.Refresh(page, 1, true))

            // Then
            val expected = PagingSource.LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = null
            )

            assertEquals(expected, result)

            verify(networkService, times(1)).getTopHeadlines(
                country = AppConstant.COUNTRY,
                page = page,
                pageSize = AppConstant.PAGE_SIZE
            )
        }
    }

    @Test
    fun fetchNews_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            // Given
            val page = 1
            val error = RuntimeException("Error Message For You")
            doThrow(error)
                .`when`(networkService).getTopHeadlines(
                    country = AppConstant.COUNTRY,
                    page = page,
                    pageSize = AppConstant.PAGE_SIZE
                )

            // When
            val result = pagingSource.load(PagingSource.LoadParams.Refresh(page, 1, false))

            // Then
            val expected = PagingSource.LoadResult.Error<Int, ApiArticle>(error)
            assertEquals(expected.toString(), result.toString())

            verify(networkService, times(1)).getTopHeadlines(
                country = AppConstant.COUNTRY,
                page = page,
                pageSize = AppConstant.PAGE_SIZE
            )
        }
    }
}

