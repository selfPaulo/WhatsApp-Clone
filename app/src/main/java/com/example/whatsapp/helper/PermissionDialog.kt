package com.example.whatsapp.helper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider()
                Text(
                    text = if(isPermanentlyDeclined) {
                        "Conceder permissão"
                    } else {
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        },
        title = {
            Text(text = "Permissão necessária")
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "Parece que você recusou permanentemente a permissão da câmera. " +
                    "Você pode acessar as configurações do aplicativo para permitir."
        } else {
            "Este aplicativo precisa de acesso à sua câmera para que seus amigos " +
                    "possam ver você em uma chamada."
        }
    }
}

class AccessMediaLocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "Parece que você recusou permanentemente a permissão de armazenamento externo. " +
                    "Você pode acessar as configurações do aplicativo para permitir."
        } else {
            "Este aplicativo precisa de acesso à sua galeria para que você possa compartilhar suas fotos " +
                    "e receber fotos também."
        }
    }
}

class RecordAudioPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "Parece que você recusou permanentemente a permissão do microfone. " +
                    "Você pode acessar as configurações do aplicativo para permitir."
        } else {
            "Este aplicativo precisa de acesso ao seu microfone para que seus amigos " +
                    "possam escutar você em uma chamada."
        }
    }
}

class PhoneCallPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "Parece que você recusou permanentemente a permissão para fazer chamadas telefônicas. " +
                    "Você pode acessar as configurações do aplicativo para permitir."
        } else {
            "Este aplicativo precisa de permissão para chamadas telefônicas para que você possa conversar " +
                    "com seus amigos."
        }
    }
}