package com.geeks.hw6_4_.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.compose.animation.*
import com.example.rickandmortycompose.R
import com.geeks.hw6_4_.ui.screens.character.CharacterScreen
import com.geeks.hw6_4_.ui.screens.Screens
import com.geeks.hw6_4_.ui.screens.character.detail.DetailCharacterScreen
import com.geeks.hw6_4_.ui.screens.episode.EpisodeScreen
import com.geeks.hw6_4_.ui.screens.episode.detail.DetailEpisodeScreen
import com.geeks.hw6_4_.ui.theme.Hw6_4_Theme
import coil.compose.AsyncImage

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hw6_4_Theme {
                RickAndMortyApp()
            }
        }
    }

    @Composable
    private fun RickAndMortyApp() {
        val navController = rememberNavController()
        Box() {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = R.drawable.bg_content,
                contentDescription = "background color",
            )
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopBar()
                },
                bottomBar = {
                    BottomBar(navController)
                }) { innerPadding ->
                // Здесь используем AnimatedNavHost
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Screens.CharacterScreen.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screens.CharacterScreen.route,
                        enterTransition = { _, _ -> slideInHorizontally(initialOffsetX = { it }) },
                        exitTransition = { _, _ -> slideOutHorizontally(targetOffsetX = { -it }) }
                    ) {
                        CharacterScreen(toDetailCharacterScreen = { characterId ->
                            navController.navigate("DetailCharacterScreen/$characterId")
                        })
                    }

                    composable(Screens.EpisodeScreen.route,
                        enterTransition = { _, _ -> fadeIn(initialAlpha = 0f) },
                        exitTransition = { _, _ -> fadeOut(targetAlpha = 0f) }
                    ) {
                        EpisodeScreen(toDetailEpisodeScreen = { episodeId ->
                            navController.navigate("DetailEpisodeScreen/$episodeId")
                        })
                    }

                    composable(
                        route = Screens.DetailCharacterScreen.route,
                        arguments = listOf(navArgument(name = "characterId") {
                            type = NavType.IntType
                        }),
                        enterTransition = { _, _ -> slideInVertically(initialOffsetY = { it }) },
                        exitTransition = { _, _ -> slideOutVertically(targetOffsetY = { -it }) }
                    ) { backStackEntry ->
                        val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                        DetailCharacterScreen(id = characterId)
                    }

                    composable(
                        route = Screens.DetailEpisodeScreen.route,
                        arguments = listOf(navArgument(name = "episodeId") {
                            type = NavType.IntType
                        }),
                        enterTransition = { _, _ -> slideInHorizontally(initialOffsetX = { it }) },
                        exitTransition = { _, _ -> slideOutHorizontally(targetOffsetX = { -it }) }
                    ) { backStackEntry ->
                        val episodeId = backStackEntry.arguments?.getInt("episodeId") ?: 0
                        DetailEpisodeScreen(
                            episodeId = episodeId,
                            toDetailCharacterScreen = { characterId ->
                                navController.navigate("DetailCharacterScreen/$characterId")
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(colorResource(R.color.purple_500)),
        title = {
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = "Rick and Morty"
            )
        }
    )
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(Screens.CharacterScreen, Screens.EpisodeScreen)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomAppBar(
        containerColor = colorResource(R.color.purple_500),
        contentColor = Color.Green
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (screen == Screens.CharacterScreen) R.drawable.ic_character
                            else R.drawable.ic_episode
                        ),
                        contentDescription = screen.route
                    )
                },
                label = {
                    Text(
                        color = Color.White,
                        text = if (screen == Screens.CharacterScreen) "Characters"
                        else "Episodes"
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
