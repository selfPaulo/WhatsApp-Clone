package com.example.whatsapp.helper

import android.net.Uri
import com.example.whatsapp.config.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class UsuarioFirebase {

    private val base64Custom = Base64Custom()
    private val configuracaoFirebase = ConfiguracaoFirebase()
    private var auth = configuracaoFirebase.getFirebaseAutenticacao()
    private lateinit var profile: UserProfileChangeRequest.Builder

    fun getIdentificadorUsuario(): String {
        val email = auth.currentUser?.email
        return base64Custom.codificarBase64(email.toString())
    }

    fun getUsuarioAtual() : FirebaseUser? {
        val usuario = configuracaoFirebase.getFirebaseAutenticacao()
        return usuario.currentUser
    }

    fun atualizarNomeUsario(nome: String) {
        profile.displayName = nome
    }

    fun atualizarFotoUsuario(foto: Uri): Boolean {
        profile.photoUri = foto
        getUsuarioAtual()?.updateProfile(profile.build())
        return true
    }

}