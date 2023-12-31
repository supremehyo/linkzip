package com.linkzip.linkzip.util.extention

import android.widget.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route : String)
= this.navigate(route){ launchSingleTop = true}


