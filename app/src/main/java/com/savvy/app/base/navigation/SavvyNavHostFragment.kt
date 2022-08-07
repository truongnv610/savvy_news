package com.savvy.app.base.navigation

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign

class SavvyNavHostFragment : NavHostFragment() {
    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        navController.navigatorProvider += CustomTabsNavigator(requireContext())
    }
}
