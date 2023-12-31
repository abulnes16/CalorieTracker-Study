package com.abulnes.tracker_data.repository

import com.abulnes.tracker_data.local.TrackerDao
import com.abulnes.tracker_data.mapper.toTrackableFood
import com.abulnes.tracker_data.mapper.toTrackedFood
import com.abulnes.tracker_data.mapper.toTrackedFoodEntity
import com.abulnes.tracker_data.remote.OpenFoodApi
import com.abulnes.tracker_data.remote.dto.Product
import com.abulnes.tracker_domain.model.TrackableFood
import com.abulnes.tracker_domain.model.TrackedFood
import com.abulnes.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.time.LocalDate

class TrackerRepositoryImpl(private val dao: TrackerDao, private val api: OpenFoodApi) :
    TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(query, page, pageSize)

            Result.success(searchDto.products.filter {
                val calculatedCalories =
                    it.nutriments.proteins100g * 4f + it.nutriments.carbohydrates100g * 4f + it.nutriments.fat100g * 9f
                val lowerBound = calculatedCalories * 0.99f
                val upperBound = calculatedCalories * 1.01f
                it.nutriments.energyKcal100g in (lowerBound..upperBound)
            }.mapNotNull { it.toTrackableFood() })

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities -> entities.map { it.toTrackedFood() } }
    }
}