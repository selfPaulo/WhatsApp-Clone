package com.example.whatsapp.model

import com.example.whatsapp.config.ConfiguracaoFirebase
import com.google.firebase.database.DatabaseReference

class Usuario {

    private val configuracaoFirebase = ConfiguracaoFirebase()

    var id: String? = null
    var nome: String? = null
    var email: String? = null
    var senha: String? = null

    fun salvar() {
        val firebaseRef = configuracaoFirebase.getFirebaseDatabase()
        val usuario: DatabaseReference? = id?.let { firebaseRef.child("usuarios").child(it) }
        usuario?.setValue(this)

    }

}