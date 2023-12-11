package com.example.whatsapp.activity.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.activity.screens.TelaConversas
import com.example.whatsapp.activity.screens.TelaAtualizacoes
import com.example.whatsapp.activity.screens.TelaChamadas

@Composable
fun BottomNavigationBar() {

    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var currentActionButton by remember {
        mutableIntStateOf(0)
    }
    var currentIcon by remember {
        mutableStateOf(Icons.Filled.Chat)
    }
    var fabCreate by remember {
        mutableStateOf(false)
    }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentDestination?.route,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        when (selected) {
                            0 -> {
                                currentActionButton = 0
                                currentIcon = Icons.Filled.Chat
                                fabCreate = false
                            },
                            1 -> {
                                currentActionButton = 1
                                currentIcon = Icons.Filled.CameraAlt
                                fabCreate = true
                            },
                            2 -> {
                                currentActionButton = 2
                                currentIcon = Icons.Filled.AddIcCall
                                fabCreate = false
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Conversas.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screens.Conversas.route) {
              TelaConversas(
                  navController
              )
            }
            composable(Screens.Status.route) {
               TelaAtualizacoes(
                   navController
               )
            }
            composable(Screens.Chamadas.route) {
                TelaChamadas(
                    navController
                )
            }       
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (fabCreate) {
            SmallFloatingActionButton(
                onClick = { /* do something */ },
                modifier = Modifier
                    .padding(end = 22.dp, bottom = 75.dp)
                    .align(alignment = Alignment.BottomEnd),
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(Icons.Filled.Create, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
            }
        }
        FloatingActionButton(
            onClick = {
                when (currentActionButton) {
                    0 -> abrirContatos()
                    1 -> abrirCamera()
                    2 -> abrirChamadas()
                }
            },
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                currentIcon,
                contentDescription = "Nova Conversa / Camêra / Nova Ligação",
                tint = Color.Black
            )
        }
    }
}

private fun abrirContatos() {
    Intent(this, ContatosActivity::class.java).also {
        startActivity(it)
    }
}

private fun abrirCamera() {
    Intent(this, CameraActivity::class.java).also {
        startActivity(it)
    }
}

private fun abrirChamadas() {
    Intent(this, ChamadasActivity::class.java).also {
        startActivity(it)
    }
}