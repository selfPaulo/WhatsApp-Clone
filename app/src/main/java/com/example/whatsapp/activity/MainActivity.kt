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
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.AddIcCall
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.whatsapp.R
import com.example.whatsapp.config.ConfiguracaoFirebase

class MainActivity : ComponentActivity() {

    private val configuracaoFirebase = ConfiguracaoFirebase()
    private var autenticacao = configuracaoFirebase.getFirebaseAutenticacao()

    private val contatos = listOf(
        "Teste 1", "Teste 2", "Teste 3"
    ).groupBy { it.first() }.toSortedMap()

    private var contatosList = contatos.map {
        Contatos(
            name = it.key.toString(),
            items = it.value
        )
    }

    //private var statusList = listOf<String>()

    private var chamadasList = listOf<String>()

    data class TabItem(
        val title: String
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name), color = MaterialTheme.colorScheme.secondary) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
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
                    colorSelected =  MaterialTheme.colorScheme.tertiary
                    tabItens.forEachIndexed { index, item ->
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
                ) { index ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
                    ) {
                        if (index == 0) {
                            if (contatosList.isEmpty()) {
                                Text(
                                    text = tabItens[index].title,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            } else {
                                ConversasLazycolum(contatos = contatosList)
                            }
                        } else if (index == 2) {
                            if (chamadasList.isEmpty()) {
                                TelaChamadasEmpty()
                            }
                        } else {
                            Text(
                                text = tabItens[index].title
                            )
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                onClick = {
                    abrirTelaContatos()
                },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Outlined.Chat,
                    contentDescription = "Contatos",
                    tint = Color.Black
                )
            }
        }
    }

    @Composable
    private fun TelaChamadasEmpty() {
        ListItem(
            headlineContent = {
            Text(
                text = "Criar link de chamada",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        },
            supportingContent = {
                Text(
                    text = "Compartilhe um link para sua chamada do WhatsApp",
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
        )
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Para fazer chamadas com seus contatos que usam o WhatsApp, toque em "
                        + Icons.Default.AddIcCall.toString() + " no fina da tela",
                color = MaterialTheme.colorScheme.tertiary
            )
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

    private fun abrirTelaLogin() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
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

data class Contatos(
    val name: String,
    val items: List<String>
)

@Composable
private fun ConversaItem(
    text: String,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        modifier = modifier
            .fillMaxWidth(),
        supportingContent = {
            Text(
                text = "Ultima msg de $text",
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        leadingContent = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
            )
        },
        trailingContent = {
            Text(text = "00:00")
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun ConversasLazycolum(
    contatos: List<Contatos>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        contatos.forEach { category ->
            items(category.items) { text ->
                ConversaItem(text = text)
            }
        }
    }
}
