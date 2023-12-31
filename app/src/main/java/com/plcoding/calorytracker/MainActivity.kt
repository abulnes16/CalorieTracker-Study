package com.plcoding.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abulnes.core.domain.preferences.Preferences
import com.abulnes.core.navigation.Route
import com.abulnes.onboarding_presentation.activity.ActivityLevelScreen
import com.abulnes.onboarding_presentation.age.AgeScreen
import com.abulnes.onboarding_presentation.gender.GenderScreen
import com.abulnes.onboarding_presentation.goal.GoalTypeScreen
import com.abulnes.onboarding_presentation.height.HeightScreen
import com.abulnes.onboarding_presentation.nutrient_goal.NutrientGaolScreen
import com.abulnes.onboarding_presentation.weight.WeightScreen
import com.abulnes.onboarding_presentation.welcome.WelcomeScreen
import com.abulnes.tracker_presentation.search.SearchScreen
import com.abulnes.tracker_presentation.tracker_overview.TrackerOverviewScreen
import com.plcoding.calorytracker.ui.theme.CaloryTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
                    NavHost(
                        navController = navController,
                        startDestination = if (shouldShowOnboarding) Route.WELCOME else Route.TRACKER_OVERVIEW,
                        modifier = Modifier.padding(it)
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNext = { navController.navigate(Route.GENDER) })
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNext = { navController.navigate(Route.HEIGHT) }
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNext = { navController.navigate(Route.AGE) })
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNext = { navController.navigate(Route.WEIGHT) }
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNext = { navController.navigate(Route.ACTIVITY) }
                            )
                        }
                        composable(Route.GOAL) {
                            GoalTypeScreen(onNext = { navController.navigate(Route.NUTRIENT_GOAL) })
                        }
                        composable(Route.ACTIVITY) {
                            ActivityLevelScreen(onNext = { navController.navigate(Route.GOAL) })
                        }

                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGaolScreen(
                                scaffoldState = scaffoldState,
                                onNext = { navController.navigate(Route.TRACKER_OVERVIEW) }
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { mealName, day, month, year ->
                                navController.navigate(
                                    "${Route.SEARCH}/$mealName/$day/$month/$year"
                                )
                            })
                        }

                        composable(route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                }, navArgument("month") {
                                    type = NavType.IntType
                                }, navArgument("year") {
                                    type = NavType.IntType
                                }
                            )) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = { navController.navigateUp() })
                        }


                    }
                }

            }
        }
    }
}