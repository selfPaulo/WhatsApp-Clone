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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsapp.R
import com.example.whatsapp.activity.ui.theme.WhatsAppTheme

class ConfiguracoesActivity : ComponentActivity() {

    @Composable
    fun createListConfiguracoes(): ArrayList<ConfiguracoesItem> {
        val configuracoes = ArrayList<ConfiguracoesItem>()

        val item1 = ConfiguracoesItem("Conta", "Notificações de segurança, mudança de número", Icons.Default.Key)
        val item2 = ConfiguracoesItem("Privacidade", "Bloqueio de contatos, mensagens temporárias", Icons.Default.Lock)
        val item3 = ConfiguracoesItem("Avatar", "Criar, editar, foto do perfil", Icons.Default.Face)
        val item4 = ConfiguracoesItem("Conversas", "Tema, Papel de parede, histórico de conversas", Icons.Filled.Chat)
        val item5 = ConfiguracoesItem("Notificações", "Mensagens, grupos, chamadas", Icons.Default.AddAlert)
        val item6 = ConfiguracoesItem("Armazenamento e Dados", "Uso de rede, dowload automático", Icons.Default.PanoramaFishEye)
        val item7 = ConfiguracoesItem("Idioma do app", "Português (Brasil) (idioma do aparelho)", Icons.Default.Circle)
        val item8 = ConfiguracoesItem("Ajuda", "Central de ajuda, fale conosco, política de privacidade", Icons.Default.Help)
        val item9 = ConfiguracoesItem("Convidar Amigos", "", Icons.Default.People)

        configuracoes.add(item1)
        configuracoes.add(item2)
        configuracoes.add(item3)
        configuracoes.add(item4)
        configuracoes.add(item5)
        configuracoes.add(item6)
        configuracoes.add(item7)
        configuracoes.add(item8)
        configuracoes.add(item9)
        return configuracoes
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
                ConfiguracoesLazycolum()
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

    data class ConfiguracoesItem(
        val name: String,
        val description: String,
        val icon: ImageVector
    )

    @Composable
    private fun ConfiguracoesItem(
        configuracoesItem: ConfiguracoesItem,
        modifier: Modifier = Modifier
    ) {
        Column {
            ListItem(
                modifier = modifier,
                headlineContent = {
                    Text(
                        text = configuracoesItem.name,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                supportingContent = {
                    Text(
                        text = configuracoesItem.description,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = configuracoesItem.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary
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
                        painter = painterResource(id = R.drawable.no_picture),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground)
                            .size(100.dp)
                    )
                },
                trailingContent = {
                  Icon(
                      painter = painterResource(id = R.drawable.qr_code),
                      contentDescription = null,
                      tint = MaterialTheme.colorScheme.primary
                  )
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
        val getConfigList = createListConfiguracoes()
        LazyColumn {
            items(getConfigList.size) { item ->
                ConfiguracoesItem(configuracoesItem = getConfigList[item])
            }
        }
    }

}

