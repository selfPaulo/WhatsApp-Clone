package com.example.whatsapp.helper

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.whatsapp.activity.ConfiguracoesActivity

class Permissao {

    @SuppressLint("ObsoleteSdkInt")
    fun validarPermissoes(permissoes: Array<String>, activity: ConfiguracoesActivity, requestCode: Int) : Boolean {

        if (Build.VERSION.SDK_INT >= 23) {
            val listarPermissoes = mutableListOf<String>()
            for (permissao in permissoes) {
                val temPermissao: Boolean = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED
                if (!temPermissao) listarPermissoes.add(permissao)
            }
            if (listarPermissoes.isEmpty()) return true
            val novasPermissoes = listarPermissoes.toTypedArray()
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode)
        }

        return true
    }

}