package com.example.whatsapp.activity.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Chat,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Conversas",
                icon = Icons.Filled.Chat,
                route = Screens.Conversas.route
            ),
            BottomNavigationItem(
                label = "Status",
                icon = Icons.Filled.Android,
                route = Screens.Status.route
            ),
            BottomNavigationItem(
                label = "Chamadas",
                icon = Icons.Filled.AddIcCall,
                route = Screens.Chamadas.route
            ),
        )
    }
}