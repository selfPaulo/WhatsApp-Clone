package com.example.whatsapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.activity.ui.theme.WhatsAppTheme
import com.example.whatsapp.R

class ContatosActivity : ComponentActivity() {

    /*
    private val names = listOf(
        "Ana",
        "André",
        "Alice",
        "Bruno",
        "Beatriz",
        "Bernardo",
        "Carlos",
        "Camila",
        "Caio",
        "Daniel",
        "Débora",
        "Davi",
        "Eduardo",
        "Elisa",
        "Enzo",
        "Felipe",
        "Fernanda",
        "Fabiana",
        "Gustavo",
        "Gabriela",
        "Guilherme",
        "Henrique",
        "Helena",
        "Heitor",
        "Igor",
        "Isabela",
        "Ingrid",
        "João",
        "Julia",
        "José",
        "Kauan",
        "Karen",
        "Kátia",
        "Lucas",
        "Laura",
        "Leandro",
        "Maria",
        "Mateus",
        "Mariana",
        "Nícolas",
        "Natália",
        "Nicole",
        "Otávio",
        "Olivia",
        "Óscar",
        "Pedro",
        "Paula",
        "Paulo",
        "Quirino",
        "Quiteria",
        "Quésia",
        "Rafael",
        "Renata",
        "Rodrigo",
        "Samuel",
        "Sara",
        "Sérgio",
        "Thiago",
        "Tatiana",
        "Túlio",
        "Ubirajara",
        "Umbelina",
        "Uriel",
        "Victor",
        "Vitória",
        "Valentina",
        "Wellington",
        "Wanessa",
        "Washington",
        "Xavier",
        "Ximena",
        "Xande",
        "Yuri",
        "Yara",
        "Yasmine",
        "Zélio",
        "Zuleide",
        "Zara"
    ).groupBy { it.first() }.toSortedMap()    

    private val namesList = names.map {
        Contato(
            name = it.key.toString(),
            items = it.value
        )
    } 
    */

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

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ContatosScreen() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        ListItem(
                            headlineContent = {
                                Text(
                                    stringResource(R.string.title_activity_contatos),
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            },
                            supportingContent = { Text(text = "x contatos") },
                        )
                    },
                    colors = TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        actionIconContentColor = Color.Transparent,
                        navigationIconContentColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = Color.Transparent
                    ),
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
                ContatosLazycolum(
                    contatos = null
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

}

data class Contato(
    val name: String,
    val items: List<String>
)

//@Composable
//private fun ContatoHeader(
//    text: String,
//    modifier: Modifier = Modifier
//) {
//    Text(
//        text = text,
//        fontSize = 16.sp,
//        fontWeight = FontWeight.Bold,
//        modifier = modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.colorScheme.primary)
//            .padding(16.dp)
//
//    )
//}

@Composable
private fun ContatoItem(
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
                text = "Recado de $text",
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        leadingContent = {
            Image(
                painter = painterResource(id = R.drawable.no_picture),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground)
                    .size(50.dp)
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun ContatosLazycolum(
    contatos: List<Contato>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        ListItem(
            headlineContent = {
                Text(
                    text = "Novo Contato",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            leadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.link),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .size(50.dp)
                )
            },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        contatos.forEach { contato ->
            items(contato.items) { text ->
                ContatoItem(text = text)
            }
        }
    }
}

