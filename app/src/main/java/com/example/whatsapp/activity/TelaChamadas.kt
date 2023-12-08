package com.example.whatsapp.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddIcCall
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.R

@Composable
fun TelaChamadas() {
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Para fazer chamadas com seus contatos que usam o WhatsApp, toque em "
                    +
                    Image(
                        imageVector = Icons.Default.AddIcCall,
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