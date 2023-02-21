package com.pavelrorecek.app

import com.pavelrorecek.app.Screen.PLAYER_DETAIL
import com.pavelrorecek.app.Screen.TEAM_DETAIL
import com.pavelrorecek.feature.playerdetail.domain.PlayerDetailNavigationController
import com.pavelrorecek.feature.playerlist.domain.PlayerListNavigationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class NavigationController :
    PlayerListNavigationController,
    PlayerDetailNavigationController {

    val navigateTo = MutableSharedFlow<Screen>()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun goToPlayerDetail() {
        scope.launch { navigateTo.emit(PLAYER_DETAIL) }
    }

    override fun goToTeamDetail() {
        scope.launch { navigateTo.emit(TEAM_DETAIL) }
    }
}
