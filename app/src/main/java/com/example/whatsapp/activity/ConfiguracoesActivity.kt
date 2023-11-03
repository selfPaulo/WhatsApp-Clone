package com.example.whatsapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsapp.R
import com.example.whatsapp.activity.ui.theme.WhatsAppTheme

class ConfiguracoesActivity : ComponentActivity() {

    private val titulosConfiguracoesString = listOf(
        "Conta", "Privacidade", "Avatar",
        "Conversas", "Notificações", "Armazenamento e dados",
        "Idioma do aplicativo", "Ajuda", "Convidar amigos"
    ).groupBy { it.chars() }

    private val titulosConfiguracoes = titulosConfiguracoesString.map {
        Configuracoes(
            name = it.key.toString(),
            items = it.value
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConfiguracoesScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ConfiguracoesScreen() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.title_activity_configuracoes),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
                    navigationIcon = {
                        IconButton(
                            onClick = { voltarTelaInicial() }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "ArrowBack",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { /* Do something */ },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                ConfiguracoesLazycolum(
                    configuracoes = titulosConfiguracoes
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Configuracoes() {
        WhatsAppTheme {
            ConfiguracoesScreen()
        }
    }

    private fun voltarTelaInicial() {
        finish()
    }

    private fun abrirTelaPerfil() {
        Intent(this, PerfilActivity::class.java).also {
            startActivity(it)
        }
    }

    data class Configuracoes(
        val name: String,
        val items: List<String>
    )

    @Composable
    private fun ConfiguracoesItem(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Column {
            ListItem(
                modifier = modifier,
                headlineContent = { Text(text = text, color = Color.White) },
                supportingContent = { Text(text = "Teste") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }

    @Composable
    private fun ConfiguracoesLazycolum(
        configuracoes: List<Configuracoes>,
        modifier: Modifier = Modifier
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    abrirTelaPerfil()
                }
        ) {
            ListItem(
                headlineContent = { Text(text = "Teste Nome", color = Color.White) },
                modifier = modifier.padding(top = 56.dp),
                supportingContent = { Text(text = "Teste Recado") },
                leadingContent = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                },
                trailingContent = {
                  Icon(imageVector = Icons.Outlined.Android, contentDescription = null)
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
        LazyColumn(modifier) {
            configuracoes.forEach { item ->
                items(item.items) { text ->
                    ConfiguracoesItem(text = text)
                }
            }
        }
    }

}

