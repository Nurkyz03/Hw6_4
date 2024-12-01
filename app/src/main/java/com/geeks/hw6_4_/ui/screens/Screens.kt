package com.geeks.hw6_4_.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.ui.graphics.Color

sealed class Screens(val route: String) {
    object CharacterScreen : Screens("CharacterScreen")
    object DetailCharacterScreen : Screens("DetailCharacterScreen/{characterId}")
    object EpisodeScreen : Screens("EpisodeScreen")
    object DetailEpisodeScreen : Screens("DetailEpisodeScreen/{episodeId}")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.CharacterScreen.route,
        enterTransition = { _, _ -> fadeIn(animationSpec = tween(500)) },
        exitTransition = { _, _ -> fadeOut(animationSpec = tween(500)) },
        popEnterTransition = { _, _ -> fadeIn(animationSpec = tween(500)) },
        popExitTransition = { _, _ -> fadeOut(animationSpec = tween(500)) }
    ) {
        composable(Screens.CharacterScreen.route) {
            // Экран списка персонажей
            CharacterScreen(navController)
        }
        composable(
            Screens.DetailCharacterScreen.route,
            arguments = listOf(navArgument("characterId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Экран подробностей персонажа
            DetailCharacterScreen(navController, backStackEntry.arguments?.getString("characterId"))
        }
        composable(Screens.EpisodeScreen.route) {
            // Экран списка эпизодов
            EpisodeScreen(navController)
        }
        composable(
            Screens.DetailEpisodeScreen.route,
            arguments = listOf(navArgument("episodeId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Экран подробностей эпизода
            DetailEpisodeScreen(navController, backStackEntry.arguments?.getString("episodeId"))
        }
    }
}

@Composable
fun CharacterScreen(navController: NavHostController) {
    // Пример экрана с персонажами
    Text(
        text = "Character Screen",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DetailCharacterScreen(navController: NavHostController, characterId: String?) {
    // Пример экрана с деталями персонажа
    Text(
        text = "Detail Character Screen: $characterId",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun EpisodeScreen(navController: NavHostController) {
    // Пример экрана с эпизодами
    Text(
        text = "Episode Screen",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DetailEpisodeScreen(navController: NavHostController, episodeId: String?) {
    // Пример экрана с деталями эпизода
    Text(
        text = "Detail Episode Screen: $episodeId",
        modifier = Modifier.fillMaxSize()
    )
}
package com.geeks.hw6_4_.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.ui.graphics.Color

sealed class Screens(val route: String) {
    object CharacterScreen : Screens("CharacterScreen")
    object DetailCharacterScreen : Screens("DetailCharacterScreen/{characterId}")
    object EpisodeScreen : Screens("EpisodeScreen")
    object DetailEpisodeScreen : Screens("DetailEpisodeScreen/{episodeId}")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.CharacterScreen.route,
        enterTransition = { _, _ -> fadeIn(animationSpec = tween(500)) },
        exitTransition = { _, _ -> fadeOut(animationSpec = tween(500)) },
        popEnterTransition = { _, _ -> fadeIn(animationSpec = tween(500)) },
        popExitTransition = { _, _ -> fadeOut(animationSpec = tween(500)) }
    ) {
        composable(Screens.CharacterScreen.route) {
            // Экран списка персонажей
            CharacterScreen(navController)
        }
        composable(
            Screens.DetailCharacterScreen.route,
            arguments = listOf(navArgument("characterId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Экран подробностей персонажа
            DetailCharacterScreen(navController, backStackEntry.arguments?.getString("characterId"))
        }
        composable(Screens.EpisodeScreen.route) {
            // Экран списка эпизодов
            EpisodeScreen(navController)
        }
        composable(
            Screens.DetailEpisodeScreen.route,
            arguments = listOf(navArgument("episodeId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Экран подробностей эпизода
            DetailEpisodeScreen(navController, backStackEntry.arguments?.getString("episodeId"))
        }
    }
}

@Composable
fun CharacterScreen(navController: NavHostController) {
    // Пример экрана с персонажами
    Text(
        text = "Character Screen",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DetailCharacterScreen(navController: NavHostController, characterId: String?) {
    // Пример экрана с деталями персонажа
    Text(
        text = "Detail Character Screen: $characterId",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun EpisodeScreen(navController: NavHostController) {
    // Пример экрана с эпизодами
    Text(
        text = "Episode Screen",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DetailEpisodeScreen(navController: NavHostController, episodeId: String?) {
    // Пример экрана с деталями эпизода
    Text(
        text = "Detail Episode Screen: $episodeId",
        modifier = Modifier.fillMaxSize()
    )
}
