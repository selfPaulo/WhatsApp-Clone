package com.example.whatsapp.activity

import android.annotation.SuppressLint
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsapp.R
import com.example.whatsapp.helper.Base64Custom
import com.example.whatsapp.ui.theme.*

class CadastroActivity : ComponentActivity() {

    private val configuracaoFirebase = ConfiguracaoFirebase()
    private var autenticacao: FirebaseAuth? = null
        private val base64Custom = Base64Custom()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CadastroScreen() {
        val usernameState = remember { mutableStateOf("") }
        val useremailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }

        fun cadastrarUsuario(usuario: Usuario) {
            autenticacao = configuracaoFirebase.getFirebaseAutenticacao()
            usuario.email?.let {
                usuario.senha?.let { it1 ->
                    autenticacao?.createUserWithEmailAndPassword(
                        it, it1
                    )?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Sucesso ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
                            finish()

                            try {
                                val identificadorUsuario = base64Custom.codificarBase64(usuario.email!!)
                                usuario.id = identificadorUsuario
                                usuario.salvar()
                            }catch (e: Exception) {
                                e.printStackTrace()
                            }

                        } else {
                            val excecao: String
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

        fun validarCadastroUsuario() {

            val textoNome = usernameState.value
            val textoEmail = useremailState.value
            val textoSenha = passwordState.value
            val usuario = Usuario()

            if (textoNome.isNotEmpty() && textoEmail.isNotEmpty() && textoSenha.isNotEmpty()) {
                usuario.nome = textoNome
                usuario.email = textoEmail
                usuario.senha = textoSenha
                cadastrarUsuario(usuario)
            } else {
                if (textoNome.isEmpty()) {
                    Toast.makeText(this, "Preencha o nome!", Toast.LENGTH_SHORT).show()
                } else if (textoEmail.isEmpty()) {
                    Toast.makeText(this, "Preencha o email!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Preencha a senha!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(title = {})
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.usuario),
                    contentDescription = stringResource(R.string.app_cadastro_description),
                    modifier = Modifier.padding(16.dp)
                )

                TextField(
                    value = usernameState.value,
                    onValueChange = { usernameState.value = it },
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
                    label = { Text(stringResource(R.string.digite_seu_nome)) },
                    modifier = Modifier.padding(16.dp)
                )

                TextField(
                    value = useremailState.value,
                    onValueChange = { useremailState.value = it },
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
                    label = { Text(stringResource(R.string.digite_seu_email)) },
                    modifier = Modifier.padding(16.dp)
                )

                TextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
                    label = { Text(stringResource(R.string.digite_sua_senha)) },
                    modifier = Modifier.padding(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Button(
                    onClick = {
                        validarCadastroUsuario()
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(stringResource(R.string.cadastrar), color = Color.Black)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Cadastro() {
        WhatsAppTheme {
            CadastroScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CadastroScreen()
                }
            }
        }
    }
}