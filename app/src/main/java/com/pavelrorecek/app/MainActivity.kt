package com.pavelrorecek.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pavelrorecek.app.Screen.PLAYER_LIST
import com.pavelrorecek.core.design.AppTheme
import com.pavelrorecek.feature.playerdetail.ui.PlayerDetailScreen
import com.pavelrorecek.feature.playerlist.ui.PlayerListScreen
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
                val navController = rememberNavController()

                NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    navController = navController,
                    startDestination = PLAYER_LIST.id,
                ) {
                    composable(PLAYER_LIST.id) { PlayerListScreen() }
                    composable(Screen.PLAYER_DETAIL.id) { PlayerDetailScreen() }
                }

                val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

                DisposableEffect(lifecycleOwner) {
                    val lifecycle = lifecycleOwner.value.lifecycle
                    val observer = LifecycleEventObserver { owner, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            owner.lifecycleScope.launch {
                                navigationController.navigateTo.filterNotNull().collect {
                                    navController.navigate(it.id)
                                }
                            }
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
