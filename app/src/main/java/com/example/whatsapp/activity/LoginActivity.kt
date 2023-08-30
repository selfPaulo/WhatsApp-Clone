package com.example.whatsapp.activity

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_cadastro.editEmail
import kotlinx.android.synthetic.main.activity_cadastro.editSenha
import kotlinx.android.synthetic.main.activity_login.editEmailLogin
import kotlinx.android.synthetic.main.activity_login.editSenhaLogin

class LoginActivity : ComponentActivity() {

    private var configuracaoFirebase: ConfiguracaoFirebase? = null
    var autenticacao: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.activity_login)
        }

        fun abrirTelaPrincipal() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        fun logarUsuario(usuario: Usuario) {
            usuario.email?.let {
                usuario.senha?.let { it1 ->
                    autenticacao?.signInWithEmailAndPassword(
                        it, it1
                    )?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            abrirTelaPrincipal()
                        } else {
                            var excecao: String = ""
                            try {
                                throw task.exception!!
                            } catch (e: FirebaseAuthInvalidUserException) {
                                excecao = "Usuário não está cadastrado!"
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                excecao = "Email e senha não correspondem"
                            } catch (e: Exception) {
                                excecao = "Erro ao logar usuário: "
                                e.printStackTrace()
                            }

                            Toast.makeText(this, excecao, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            fun validarAutenticacaoUsuario(view: View) {
                autenticacao = configuracaoFirebase?.getFirebaseAutenticacao()
                var email = editEmailLogin.text.toString()
                var senha = editSenhaLogin.text.toString()
                var usuario = Usuario()

                if (email.isNotEmpty()) {
                    if (senha.isNotEmpty()) {
                        usuario.email = email
                        usuario.senha = senha

                        logarUsuario(usuario)

                    } else {
                        Toast.makeText(this, "Preencha o campo de senha", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Preencha o campo de email", Toast.LENGTH_SHORT).show()
                }

            }

            fun abrirTelaCadastro(view: View) {
                val intent = Intent(this, CadastroActivity::class.java)
                startActivity(intent)
            }

        }
    }
}
