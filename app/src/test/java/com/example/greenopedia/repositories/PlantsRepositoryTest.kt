package com.example.greenopedia.repositories

import com.example.greenopedia.data.local.PlantsDao
import com.example.greenopedia.data.remote.TrefleAPI
import com.example.greenopedia.data.remote.responses.Data
import com.example.greenopedia.data.remote.responses.Meta
import com.example.greenopedia.data.remote.responses.PlantsResponse
import com.example.greenopedia.utils.ErrorMessages
import com.example.greenopedia.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response


@ExperimentalCoroutinesApi
class PlantsRepositoryTest : PlantsRepositoryImpl {

    private lateinit var repository: PlantsRepository

    @Mock
    private lateinit var mockDao: PlantsDao

    @Mock
    private lateinit var mockAPI: TrefleAPI

    private lateinit var closeable: AutoCloseable

    @Before
    fun setup() {
        closeable = MockitoAnnotations.openMocks(this)
        repository = PlantsRepository(mockDao, mockAPI)
    }

    @After
    fun tearDown() {
        closeable.close()
        Mockito.reset(mockDao, mockAPI)
    }

    @Test
    fun `fetchAllPlants insert plant to DB when API call is successful`() = runTest {
        val plantsResponse = PlantsResponse(mutableListOf(), null, Meta(0))
        val apiResponse = Response.success(plantsResponse)
        whenever(mockAPI.getAllPlants(pageNumber = 1)).thenReturn(apiResponse)

        val result = repository.fetchAllPlants(1)

        assertThat(result is Resource.Success).isTrue()
        assertThat(plantsResponse).isEqualTo(result.data)
        verify(mockDao).insertAllPlants(plantsResponse.plantsList)
    }

    @Test
    fun `fetchAllPlants returns error when API call fails`() = runTest {
        val errorBody = ResponseBody.create(
            "application/json".toMediaTypeOrNull(),
            "{ \"error\": \"Error message\" }"
        )
        val apiResponse = Response.error<PlantsResponse>(400, errorBody)
        whenever(mockAPI.getAllPlants(pageNumber = 1)).thenReturn(apiResponse)

        val result = repository.fetchAllPlants(1)

        assertThat(result is Resource.Error).isTrue()
        assertThat(result.message).isEqualTo("Response.error()")
    }

    @Test
    fun `fetchAllPlants returns no internet connection error on exception`() = runTest {
        whenever(mockAPI.getAllPlants(pageNumber = 1)).thenThrow(RuntimeException("Network Error"))

        val result = repository.fetchAllPlants(1)

        assertThat(result is Resource.Error).isTrue()
        assertThat(ErrorMessages.UNKNOWN_ERROR).isEqualTo(result.message)
    }

    @Test
    fun `loadLocalPlants returns success when there is local data`() = runTest {
        val plant1 = Data(
            plantId = 1,
            commonName = "Rose1",
            year = 1748,
            family = "flower",
            author = "esraa",
            zone = "pal"
        )
        val plant2 = Data(
            plantId = 2,
            commonName = "Rose2",
            year = 1748,
            family = "flower",
            author = "esraa",
            zone = "sud"
        )
        val plant3 = Data(
            plantId = 3,
            commonName = "Rose3",
            year = 1748,
            family = "flower",
            author = "esraa",
            zone = "pal"
        )
        val localPlants = listOf(plant1, plant2, plant3)
        whenever(mockDao.getAllPlants()).thenReturn(localPlants)
        whenever(mockDao.getCount()).thenReturn(localPlants.size)

        val result = repository.loadLocalPlants()

        assertThat(result is Resource.Success).isTrue()
        assertThat(localPlants).isEqualTo(result.data?.plantsList)
        assertThat(localPlants.size).isEqualTo(result.data?.meta?.total)
        verify(mockDao).getAllPlants()
        verify(mockDao).getCount()
    }

    @Test
    fun `loadLocalPlants returns success with empty list when no data in database`() = runTest {
        val emptyList = mutableListOf<Data>()
        whenever(mockDao.getAllPlants()).thenReturn(emptyList)
        whenever(mockDao.getCount()).thenReturn(0)

        val result = repository.loadLocalPlants()

        assertThat(result is Resource.Success).isTrue()
        assertThat(emptyList).isEqualTo(result.data?.plantsList)
        assertThat(0).isEqualTo(result.data?.meta?.total)
        verify(mockDao).getAllPlants()
        verify(mockDao).getCount()
    }

    @Test
    fun `loadLocalPlants returns error when database throws exception`() = runTest {
        whenever(mockDao.getAllPlants()).thenThrow(RuntimeException("Database Error"))

        val result = repository.loadLocalPlants()

        assertThat(result is Resource.Error).isTrue()
        assertThat(result.data).isNull()
    }

    override suspend fun fetchAllPlants(pageNum: Int): Resource<PlantsResponse> {
        TODO("for testing viewmodel behaviour")
    }

    override suspend fun fetchPlantsByFilter(
        filterId: String,
        pageNum: Int
    ): Resource<PlantsResponse> {
        TODO("for testing viewmodel behaviour")
    }

    override suspend fun loadLocalPlants(): Resource<PlantsResponse> {
        TODO("for testing viewmodel behaviour")
    }

    override suspend fun loadLocalPlantsByFilter(zone: String): Resource<PlantsResponse> {
        TODO("for testing viewmodel behaviour")
    }
}