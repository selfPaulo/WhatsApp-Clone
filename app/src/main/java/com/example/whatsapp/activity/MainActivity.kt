package com.example.whatsapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.whatsapp.activity.ui.theme.WhatsAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.AddIcCall
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.whatsapp.R.*
import com.example.whatsapp.R.mipmap.*
import com.example.whatsapp.config.ConfiguracaoFirebase

class MainActivity : ComponentActivity() {

    private val configuracaoFirebase = ConfiguracaoFirebase()
    private var autenticacao = configuracaoFirebase.getFirebaseAutenticacao()

    data class TabItem(
        val title: String,
        val index: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun MainScreen() {
        var currentActionButton by remember {
            mutableIntStateOf(0)
        }
        var currentIcon by remember {
            mutableStateOf(Icons.Filled.Chat)
        }
        var fabCreate by remember {
            mutableStateOf(false)
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(string.app_name),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
                    actions = {
                        IconButton(
                            onClick = { /* Do something */ },
                        ) {
                            Icon(
                                Icons.Outlined.CameraAlt,
                                contentDescription = "Camera",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                        IconButton(
                            onClick = { /* Do something */ },
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                        var expanded by remember { mutableStateOf(false) }
                        IconButton(
                            onClick = { expanded = true },
                        ) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "More",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    text = {
                                        Text(
                                            text = "Novo Grupo",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    text = {
                                        Text(
                                            text = "Nova Transmissão",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    text = {
                                        Text(
                                            text = "Aparelhos Conectados",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    text = {
                                        Text(
                                            text = "Mensagens Favoritas",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    text = {
                                        Text(
                                            text = "Encontrar Empresas",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    text = {
                                        Text(
                                            text = "Pagamentos",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = {
                                        abrirTelaConfiguracoes()
                                        expanded = false
                                    },
                                    text = {
                                        Text(
                                            text = "Configurações",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = {
                                        deslogarUsuario()
                                        finish()
                                    },
                                    text = {
                                        Text(
                                            text = "Sair",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                            }
                        }
                    }
                )
            }
        ) {
            val tabItens = listOf(
                TabItem(
                    title = "Conversas",
                    index = 0
                ),
                TabItem(
                    title = "Atualizações",
                    index = 1
                ),
                TabItem(
                    title = "Chamadas",
                    index = 2
                )
            )
            var selectedTabIndex by remember {
                mutableIntStateOf(0)
            }
            val pagerState = rememberPagerState {
                tabItens.size
            }
            var colorSelected: Color
            LaunchedEffect(selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp)
            ) {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabItens.forEachIndexed { index, item ->
                        colorSelected = if (item.index == selectedTabIndex) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.tertiary
                        }
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(text = item.title, color = colorSelected)
                            }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        when (selectedTabIndex) {
                            0 -> {
                                TelaConversas()
                                currentActionButton = 0
                                currentIcon = Icons.Filled.Chat
                                fabCreate = false
                            }
                            1 -> {
                                TelaAtualizacoes()
                                currentActionButton = 1
                                currentIcon = Icons.Filled.CameraAlt
                                fabCreate = true
                            }
                            else -> {
                                TelaChamadas()
                                currentActionButton = 2
                                currentIcon = Icons.Filled.AddIcCall
                                fabCreate = false
                            }
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (fabCreate) {
                SmallFloatingActionButton(
                    onClick = { /* do something */ },
                    modifier = Modifier
                        .padding(end = 22.dp, bottom = 75.dp)
                        .align(alignment = Alignment.BottomEnd),
                    containerColor = MaterialTheme.colorScheme.tertiary
                ) {
                    Icon(Icons.Filled.Create, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                }
            }
            FloatingActionButton(
                onClick = {
                    when (currentActionButton) {
                        0 -> abrirTelaContatos()
                        1 -> abrirTelaContatos()
                        2 -> abrirTelaContatos()
                    }
                },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    currentIcon,
                    contentDescription = "Nova Conversa / Camêra / Nova Ligação",
                    tint = Color.Black
                )
            }
        }
    }

    private fun abrirTelaConfiguracoes() {
        Intent(this, ConfiguracoesActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun abrirTelaContatos() {
        Intent(this, ContatosActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun abrirTelaLogin() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun deslogarUsuario() {
        try {
            autenticacao.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStart() {
        super.onStart()
        val usuarioAtual = autenticacao.currentUser
        if (usuarioAtual == null) {
            abrirTelaLogin()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Main() {
        WhatsAppTheme {
            MainScreen()
        }
    }
}