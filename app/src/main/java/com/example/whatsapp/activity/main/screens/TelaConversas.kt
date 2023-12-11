package com.example.whatsapp.activity.main.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.R

/*data class Contatos(
    val name: String,
    val items: List<String>
)

val contatos = listOf(
    "Teste 1", "Teste 2", "Teste 3"
).groupBy { it.first() }.toSortedMap()

var contatosList = contatos.map {
    Contatos(
        name = it.key.toString(),
        items = it.value
    )
}
*/

val contatos = null

@Composable
fun ConversaItem(
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
                painter = painterResource(id = R.drawable.no_picture),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground)
                    .size(50.dp)
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
fun ConversasLazycolum(
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

@Composable
fun ConversasEmpty() {
        Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Para iniciar conversa com seus contatos que usam o WhatsApp, toque em "
                    +
                    Image(
                        imageVector = Icons.Default.Chat,
                        modifier = Modifier.size(25.dp),
                        contentDescription = null
                    )
                    +
                    " no fina da tela",
            modifier = Modifier.padding(25.dp, 25.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun TelaConversas(navController: NavController) {
    if (contatos != null) {
        ConversasLazycolum(contatos = contatosList)
    } else {
        ConversasEmpty()
    }
}