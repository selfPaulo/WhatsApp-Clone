package com.example.whatsapp.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.whatsapp.R
import com.example.whatsapp.config.ConfiguracaoFirebase
import com.example.whatsapp.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_cadastro.editEmail
import kotlinx.android.synthetic.main.activity_cadastro.editNome
import kotlinx.android.synthetic.main.activity_cadastro.editSenha

class CadastroActivity : ComponentActivity() {

    private var configuracaoFirebase: ConfiguracaoFirebase? = null
    var autenticacao: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.activity_cadastro)
        }
    }

    fun cadastrarUsuario(usuario: Usuario) {
        autenticacao = configuracaoFirebase?.getFirebaseAutenticacao()
        usuario.email?.let {
            usuario.senha?.let { it1 ->
                autenticacao?.createUserWithEmailAndPassword(
                    it, it1
                )?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sucesso ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        var excecao: String = ""
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            excecao = "Digite uma senha mais forte!"
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            excecao = "Por favor, digite um e-mail válido"
                        } catch (e: FirebaseAuthUserCollisionException) {
                            excecao = "Esta conta já foi cadastrada"
                        } catch (e: Exception) {
                            excecao = "Erro ao cadastrar usuário: "
                            e.printStackTrace()
                        }

                        Toast.makeText(this, excecao, Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    fun validarCadastroUsuario(view: View) {

        var textoNome = editNome.text.toString()
        var textoEmail = editEmail.text.toString()
        var textoSenha = editSenha.text.toString()
        var usuario = Usuario()

        if (textoNome.isNotEmpty()) {
            if (textoEmail.isNotEmpty()) {
                if (textoSenha.isNotEmpty()) {
                    usuario.nome = textoNome
                    usuario.email = textoEmail
                    usuario.senha = textoSenha

                    cadastrarUsuario(usuario)

                } else {
                    Toast.makeText(this, "Preencha a senha!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha o email!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Preencha o nome!", Toast.LENGTH_SHORT).show()
        }

    }


}