package com.abulnes.tracker_data.mapper

import com.abulnes.tracker_data.local.entity.TrackedFoodEntity
import com.abulnes.tracker_domain.model.MealType
import com.abulnes.tracker_domain.model.TrackedFood
import java.time.LocalDate


fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id,
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        name = name,
        calories = calories,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        id = id
    )
}