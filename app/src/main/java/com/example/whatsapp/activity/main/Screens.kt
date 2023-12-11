package com.example.whatsapp.activity.main

sealed class Screens(val route : String) {
    object Conversas : Screens("conversa_route")
    object Status : Screens("status_route")
    object Chamadas : Screens("chamadas_route")
}