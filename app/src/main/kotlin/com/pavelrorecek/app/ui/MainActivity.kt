package com.pavelrorecek.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.pavelrorecek.app.ui.Screen.PLAYER_DETAIL
import com.pavelrorecek.app.ui.Screen.PLAYER_LIST
import com.pavelrorecek.app.ui.Screen.TEAM_DETAIL
import com.pavelrorecek.core.design.AppTheme
import com.pavelrorecek.feature.playerdetail.ui.PlayerDetailScreen
import com.pavelrorecek.feature.playerlist.ui.PlayerListScreen
import com.pavelrorecek.feature.teamdetail.ui.TeamDetailScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

public class MainActivity : ComponentActivity(), KoinComponent {

    private val navigationController = get<NavigationController>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val navController = rememberAnimatedNavController()

                AnimatedNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    navController = navController,
                    startDestination = PLAYER_LIST.id,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                ) {
                    composable(PLAYER_LIST.id) { PlayerListScreen() }
                    composable(PLAYER_DETAIL.id) { PlayerDetailScreen() }
                    composable(TEAM_DETAIL.id) { TeamDetailScreen() }
                }

                // Listen to navigateTo events and navigate
                val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
                DisposableEffect(lifecycleOwner) {
                    var navigationJob: Job? = null
                    val lifecycle = lifecycleOwner.value.lifecycle
                    val observer = LifecycleEventObserver { owner, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            navigationJob = owner.lifecycleScope.launch {
                                navigationController.navigateTo.filterNotNull().collect {
                                    navController.navigate(it.id)
                                }
                            }
                        }
                        if (event == Lifecycle.Event.ON_PAUSE) {
                            navigationJob?.cancel()
                            navigationJob = null
                        }
                    }

                    lifecycle.addObserver(observer)
                    onDispose {
                        lifecycle.removeObserver(observer)
                    }
                }
            }
        }
    }
}
