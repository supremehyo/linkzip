package com.linkzip.linkzip.util.extention

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route : String)
= this.navigate(route){ launchSingleTop = true}