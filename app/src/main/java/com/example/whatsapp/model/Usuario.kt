package com.example.whatsapp.model

import com.example.whatsapp.helper.Base64Custom
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Usuario {

    private val base64Custom = Base64Custom()
    private lateinit var firebaseRef: DatabaseReference

    var id: String? = null
    var nome: String? = null
    var email: String? = null
    var senha: String? = null

    fun salvar() {
        senha = this.senha?.let { base64Custom.codificarBase64(it) }
        firebaseRef = this.id?.let { FirebaseDatabase.getInstance().getReference("Usuarios").child(it) }!!
        firebaseRef.setValue(this)
    }

}