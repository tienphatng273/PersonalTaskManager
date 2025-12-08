package com.example.personaltaskmanager.features.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.personaltaskmanager.R
import com.example.personaltaskmanager.features.calendar_events.screens.CalendarFragment
import com.example.personaltaskmanager.features.task_manager.screens.TaskListFragment

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        val composeNav = findViewById<ComposeView>(R.id.bottom_nav_compose)

        composeNav.setContent {
            var currentItemState by remember { mutableStateOf(NavItem.TASKS) }

            BottomNavBar(
                current = currentItemState,
                onSelect = { selected ->
                    currentItemState = selected
                    navigateTo(selected)
                }
            )
        }

        navigateTo(NavItem.TASKS)
    }

    private fun navigateTo(item: NavItem) {
        val fragment: Fragment = when (item) {
            NavItem.TASKS -> TaskListFragment()
            NavItem.CALENDAR -> CalendarFragment()
            NavItem.HABIT -> HabitFragment()
            NavItem.SETTINGS -> SettingsFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_container, fragment)
            .commit()
    }
}
