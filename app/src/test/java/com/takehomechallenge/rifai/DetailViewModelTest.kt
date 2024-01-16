@file:Suppress("DEPRECATION")

package com.takehomechallenge.rifai


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.takehomechallenge.rifai.database.FavoriteEntity
import com.takehomechallenge.rifai.database.FavoriteRepository
import com.takehomechallenge.rifai.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var testScope: TestCoroutineScope

    @Mock
    private lateinit var repository: FavoriteRepository

    @Mock
    private lateinit var favoriteStatusObserver: Observer<Boolean>

    @Mock
    private lateinit var favoriteUserObserver: Observer<FavoriteEntity>

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testDispatcher = TestCoroutineDispatcher()
        testScope = TestCoroutineScope(testDispatcher)
        Dispatchers.setMain(testDispatcher)

        viewModel = DetailViewModel(repository)
        viewModel.favoriteStatus.observeForever(favoriteStatusObserver)
    }

    @Test
    fun `test addFavoriteUser`() = testScope.runBlockingTest {
        val favoriteUser = FavoriteEntity("mockUsername", "image", "species", "gender", "", "")

        viewModel.addFavoriteUser(favoriteUser)

        verify(repository).insertUser(favoriteUser)
        verify(favoriteStatusObserver).onChanged(true)
    }

    @Test
    fun `test deleteFavoriteUser`() = testScope.runBlockingTest {
        val favoriteUser = FavoriteEntity("mockUsername", "image", "species", "gender", "", "")

        viewModel.deleteFavoriteUser(favoriteUser)

        verify(repository).deleteUser(favoriteUser)
        verify(favoriteStatusObserver).onChanged(false)
    }

    @Test
    fun `test getFavoriteUserByUsername`() = testScope.runBlockingTest {
        val mockFavoriteEntity = FavoriteEntity("mockUsername", "image", "species", "gender", "", "")
        `when`(repository.getFavoriteEntityByUsername("mockUsername")).thenReturn(
            MutableLiveData<FavoriteEntity>().apply { value = mockFavoriteEntity })

        // Observe the LiveData returned by getFavoriteUserByUsername
        viewModel.getFavoriteUserByUsername("mockUsername").observeForever(favoriteUserObserver)

        val result = viewModel.getFavoriteUserByUsername("mockUsername").value

        verify(favoriteUserObserver).onChanged(mockFavoriteEntity)
        assert(result == mockFavoriteEntity)
    }
}
