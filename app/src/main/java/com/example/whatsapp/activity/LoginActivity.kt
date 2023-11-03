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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsapp.R
import com.example.whatsapp.R.drawable.*
import com.example.whatsapp.activity.ui.theme.WhatsAppTheme

class LoginActivity : ComponentActivity() {
    private val configuracaoFirebase = ConfiguracaoFirebase()
    private var autenticacao = configuracaoFirebase.getFirebaseAutenticacao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "PrivateResource")
    @Composable
    fun LoginScreen() {
        val usernameState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility)
            painterResource(id = com.google.android.material.R.drawable.design_ic_visibility)
        else
            painterResource(id = com.google.android.material.R.drawable.design_ic_visibility_off)

        fun logarUsuario(usuario: Usuario) {
            usuario.email?.let {
                usuario.senha?.let { it1 ->
                    autenticacao.signInWithEmailAndPassword(
                        it, it1
                    ).addOnCompleteListener(this) { task ->
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
            autenticacao = configuracaoFirebase.getFirebaseAutenticacao()
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

        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = stringResource(R.string.app_logo_description),
                    modifier = Modifier.padding(16.dp)
                )

                TextField(
                    value = usernameState.value,
                    onValueChange = { usernameState.value = it },
                    modifier = Modifier.padding(16.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
                    label = { Text(stringResource(R.string.digite_seu_email)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    ),
                    shape = RoundedCornerShape(25.dp)
                )

                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    modifier = Modifier.padding(16.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
                    label = { Text(stringResource(R.string.digite_sua_senha)) },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(painter = icon, contentDescription = "Icone de visibilidade")
                        }
                    },
                    isError = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    ),
                    shape = RoundedCornerShape(25.dp)
                )

                Button(
                    onClick = {
                        validarAutenticacaoUsuario()
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(stringResource(R.string.logar), color = Color.Black)
                }

                TextButton(
                    onClick = {
                        abrirTelaCadastro()
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(stringResource(R.string.nao_tem_conta),  color = Color.Black)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Login() {
        WhatsAppTheme {
            LoginScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        val usuarioAtual = autenticacao.currentUser
        if (usuarioAtual != null) {
            abrirTelaPrincipal()
        } 
    }


    private fun abrirTelaCadastro() {
        Intent(this, CadastroActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun abrirTelaPrincipal() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}
