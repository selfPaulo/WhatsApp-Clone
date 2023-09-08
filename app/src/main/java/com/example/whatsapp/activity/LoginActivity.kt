package com.example.whatsapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.example.whatsapp.config.ConfiguracaoFirebase
import com.example.whatsapp.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsapp.ui.theme.*
import com.example.whatsapp.R.drawable.*

class LoginActivity : ComponentActivity() {
    private var configuracaoFirebase: ConfiguracaoFirebase? = null
    private var autenticacao: FirebaseAuth? = null

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen() {
        val usernameState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }

        fun logarUsuario(usuario: Usuario) {
            usuario.email?.let {
                usuario.senha?.let { it1 ->
                    autenticacao?.signInWithEmailAndPassword(
                        it, it1
                    )?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            abrirTelaPrincipal()
                        } else {
                            val excecao: String
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
        }

        fun validarAutenticacaoUsuario() {
            autenticacao = configuracaoFirebase?.getFirebaseAutenticacao()
            val email = usernameState.value
            val senha = passwordState.value
            val usuario = Usuario()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                usuario.email = email
                usuario.senha = senha
                logarUsuario(usuario)
            } else {
                if (email.isEmpty()) {
                    Toast.makeText(this, "Preencha o campo de email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Preencha o campo de senha", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Scaffold(
            topBar = {
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(Green80),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = "WhatsApp logo",
                    modifier = Modifier.padding(16.dp)
                )

                TextField(
                    value = usernameState.value,
                    onValueChange = { usernameState.value = it },
                    label = { Text("E-mail") },
                    modifier = Modifier.padding(16.dp)
                )

                TextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Senha") },
                    modifier = Modifier.padding(16.dp),
                    //keyboardType = KeyboardType.Password
                )

                Button(
                    onClick = {
                        validarAutenticacaoUsuario()
                    },
                    colors = ButtonDefaults.buttonColors(Accent80),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Logar", color = Color.Black)
                }

                Button(
                    onClick = {
                        abrirTelaCadastro()
                    },
                    colors = ButtonDefaults.buttonColors(Accent80),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Não tem conta?",  color = Color.Black)
                }
            }
        }
    }

    private fun Image(imageVector: Int, contentDescription: String, modifier: Modifier) {

    }

    @Preview(showBackground = true)
    @Composable
    fun Login() {
        LoginScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
    override fun onStart() {
        super.onStart()
        val usuarioAtual = autenticacao?.currentUser
        if (usuarioAtual != null) {
            abrirTelaPrincipal()
        }
    }

    fun abrirTelaCadastro() {
        val intent = Intent(this, CadastroActivity::class.java)
        startActivity(intent)
    }

    fun abrirTelaPrincipal() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
