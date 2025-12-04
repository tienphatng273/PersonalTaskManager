package com.example.personaltaskmanager.features.navigation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

import com.example.personaltaskmanager.R
import com.example.personaltaskmanager.features.task_manager.screens.TaskManagerMainActivity
import com.example.personaltaskmanager.features.calendar_events.screens.CalendarMonthActivity
import com.example.personaltaskmanager.ui.settings.SettingsActivity

class NavigationActivity : AppCompatActivity() {

    private var currentItem: NavItem = NavItem.TASKS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        val composeNav = findViewById<ComposeView>(R.id.bottom_nav_compose)

        composeNav.setContent {
            BottomNavBar(currentItem) { selected ->
                currentItem = selected
                navigateTo(selected)
            }
        }

        navigateTo(NavItem.TASKS)
    }

    private fun navigateTo(item: NavItem) {
        val fragment: Fragment = when (item) {
            NavItem.TASKS -> TaskManagerFragment()
            NavItem.CALENDAR -> CalendarFragment()
            NavItem.SETTINGS -> SettingsFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_container, fragment)
            .commit()
    }
}
