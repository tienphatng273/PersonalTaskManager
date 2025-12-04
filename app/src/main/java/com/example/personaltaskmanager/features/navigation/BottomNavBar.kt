package com.example.personaltaskmanager.features.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.personaltaskmanager.R

/**
 * Điều hướng gồm 3 tab: Tasks, Calendar, Settings
 */
enum class NavItem(val icon: Int) {
    TASKS(R.drawable.feature_task_manager_ic_calendar),
    CALENDAR(R.drawable.feature_calendar_ic_calendar),
    SETTINGS(android.R.drawable.ic_menu_preferences)
}

@Composable
fun BottomNavBar(
    current: NavItem,
    onSelect: (NavItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavItem.values().forEach { item ->
            IconButton(onClick = { onSelect(item) }) {
                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = item.name,
                    tint = if (item == current)
                        MaterialTheme.colorScheme.primary
                    else Color.Gray
                )
            }
        }
    }
}
