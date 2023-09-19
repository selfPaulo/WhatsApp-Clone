package com.example.whatsapp.helper

import android.util.Base64

class Base64Custom {

    val regex = Regex("(\\n|\\r)")

    fun codificarBase64(texto: String) : String {
        return Base64.encodeToString(texto.toByteArray(), Base64.DEFAULT).replace(regex = regex, replacement = "")
    }

    fun decodificarBase64(textoCodificado: String) : String {
        return String(Base64.decode(textoCodificado, Base64.DEFAULT))
    }

}