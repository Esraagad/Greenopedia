package com.example.greenopedia.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.greenopedia.data.remote.responses.Data
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.test.runTest


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class PlantsDaoTest {

    private lateinit var database: PlantsDatabase
    private lateinit var plantsDao: PlantsDao

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, PlantsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        plantsDao = database.getPlantsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getCount() = runTest {
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
        val plantsList = listOf(plant1, plant2, plant3)

        plantsDao.insertAllPlants(plantsList)
        val loadedPlants = plantsDao.getAllPlants()

        assertThat(loadedPlants.size).isEqualTo(3)
    }

    @Test
    fun insertAllPlants() = runTest {
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
        val plantsList = listOf(plant1, plant2, plant3)

        plantsDao.insertAllPlants(plantsList)
        val loadedPlants = plantsDao.getAllPlants()

        assertThat(loadedPlants[0].commonName).isEqualTo("Rose1")
        assertThat(loadedPlants[1].zone).isEqualTo("sud")
    }

    @Test
    fun getPlantByFilter() = runTest {
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
        val plantsList = listOf(plant1, plant2, plant3)

        plantsDao.insertAllPlants(plantsList)
        val loadedPlants = plantsDao.getPlantsByFilter("pal")

        assertThat(loadedPlants.size).isEqualTo(2)
    }

}