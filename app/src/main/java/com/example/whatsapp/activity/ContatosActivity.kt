package com.example.whatsapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.activity.ui.theme.WhatsAppTheme
import com.example.whatsapp.R

class ContatosActivity : ComponentActivity() {

    private val names = listOf(
        "Ana", "André", "Alice", "Bruno", "Beatriz", "Bernardo", "Carlos", "Camila", "Caio", "Daniel", "Débora", "Davi", "Eduardo", "Elisa", "Enzo", "Felipe", "Fernanda", "Fabiana", "Gustavo", "Gabriela", "Guilherme", "Henrique", "Helena", "Heitor", "Igor", "Isabela", "Ingrid", "João", "Julia", "José", "Kauan", "Karen", "Kátia", "Lucas", "Laura", "Leandro", "Maria", "Mateus", "Mariana", "Nícolas", "Natália", "Nicole", "Otávio", "Olivia", "Óscar", "Pedro", "Paula", "Paulo", "Quirino", "Quiteria", "Quésia", "Rafael", "Renata", "Rodrigo", "Samuel", "Sara", "Sérgio", "Thiago", "Tatiana", "Túlio", "Ubirajara", "Umbelina", "Uriel", "Victor", "Vitória", "Valentina", "Wellington", "Wanessa", "Washington", "Xavier", "Ximena", "Xande", "Yuri", "Yara", "Yasmine", "Zélio", "Zuleide", "Zara"
    ).groupBy { it.first() }.toSortedMap()

    private val namesList = names.map {
        Category(
            name = it.key.toString(),
            items = it.value
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ContatosScreen() {
        Scaffold (
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.title_activity_contatos),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                    navigationIcon = {
                        IconButton(
                            onClick = { voltarTelaInicial() }
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
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
                                            text = "Convidar Amigos",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Contatos",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Atualizar",
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { /* Do something */ },
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = {
                                        Text(
                                            text = "Ajuda",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                CategorizedLazycolum(
                    categories = namesList
                )
            }
        }
    }

    private fun voltarTelaInicial() {
        finish()
    }

    @Preview(showBackground = true)
    @Composable
    fun Contatos() {
        WhatsAppTheme {
            ContatosScreen()
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
                    ContatosScreen()
                }
            }
        }
    }
}

data class Category(
    val name: String,
    val items: List<String>
)

@Composable
private fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)

    )
}

@Composable
private fun CategoryItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)

    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategorizedLazycolum(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        categories.forEach { category ->
            stickyHeader {
                CategoryHeader(text = (category.name))
            }
            items(category.items) {text ->
                CategoryItem(text = text)
            }
        }
    }
}

