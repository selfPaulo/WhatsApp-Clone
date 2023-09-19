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
import com.example.whatsapp.ui.theme.WhatsAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.whatsapp.R
import com.example.whatsapp.config.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private val configuracaoFirebase = ConfiguracaoFirebase()
    private var autenticacao: FirebaseAuth? = null

    data class TabItem(
        val title: String
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun MainScreen() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name), color = MaterialTheme.colorScheme.secondary) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                    actions = {
                        IconButton(
                            onClick = { /* Do something */ },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                Icons.Outlined.CameraAlt,
                                contentDescription = "Camera",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
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
                        var expanded by remember { mutableStateOf(false) }
                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "More",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            DropdownMenu(
                                expanded = expanded,
                                modifier = Modifier.padding(top = 16.dp),
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Novo Grupo",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Nova Transmissão",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Aparelhos Conectados",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Mensagens Favoritas",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Encontrar Empresas",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Pagamentos",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { abrirTelaConfiguracoes() },
                                    modifier = Modifier.padding(start = 16.dp),
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
                                    modifier = Modifier.padding(start = 16.dp),
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
                    title = "Conversas"
                ),
                TabItem(
                    title = "Atualizações"
                ),
                TabItem(
                    title = "Chamadas"
                )
            )
            var selectedTabIndex by remember {
                mutableStateOf(0)
            }
            val pagerState = rememberPagerState {
                tabItens.size
            }
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
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(text = item.title, color = MaterialTheme.colorScheme.secondary)
                            }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { index ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = tabItens[index].title)
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                onClick = {
                },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    Icons.Outlined.Chat,
                    contentDescription = "Contatos",
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

    private fun deslogarUsuario() {
        try {
            autenticacao?.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Main() {
        WhatsAppTheme {
            MainScreen()
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
                    MainScreen()
                }
            }
        }
    }
}
