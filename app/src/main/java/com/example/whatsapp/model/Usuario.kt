package com.example.whatsapp.model

import android.util.Log
import com.example.whatsapp.config.ConfiguracaoFirebase
import com.example.whatsapp.helper.Base64Custom
import com.google.firebase.database.FirebaseDatabase

class Usuario {

    private val base64Custom = Base64Custom()
    private val configuracaoFirebase = ConfiguracaoFirebase()
    private var firebaseRef = configuracaoFirebase.getFirebaseDatabase()

    var id: String? = null
    var nome: String? = null
    var email: String? = null
    var senha: String? = null

//    fun getData() {
//        val email = configuracaoFirebase.getFirebaseAutenticacao().currentUser?.email
//        firebaseRef.child("usuarios").child(base64Custom.codificarBase64(email.toString())).child("email").get().addOnSuccessListener {
//            return@addOnSuccessListener
//        }
//    }

    fun salvar() {
        senha = this.senha?.let { base64Custom.codificarBase64(it) }
        firebaseRef = this.id?.let { FirebaseDatabase.getInstance().getReference("Usuarios").child(it) }!!
        firebaseRef.setValue(this)
    }

}