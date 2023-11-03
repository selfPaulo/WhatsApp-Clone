package com.example.whatsapp.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whatsapp.R
import com.example.whatsapp.activity.ui.theme.WhatsAppTheme
import com.example.whatsapp.config.ConfiguracaoFirebase
import com.example.whatsapp.helper.Base64Custom
import com.example.whatsapp.helper.CameraPermissionTextProvider
import com.example.whatsapp.helper.MainViewModel
import com.example.whatsapp.helper.PermissionDialog
import com.example.whatsapp.helper.PhoneCallPermissionTextProvider
import com.example.whatsapp.helper.ReadExternalStoragePermissionTextProvider
import com.example.whatsapp.helper.RecordAudioPermissionTextProvider
import com.example.whatsapp.model.Usuario

@SuppressLint("QueryPermissionsNeeded")
fun Activity.openAppSettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

class PerfilActivity : ComponentActivity() {

    private val configuracaoFirebase = ConfiguracaoFirebase()

    @RequiresApi(Build.VERSION_CODES.R)
    private val permissionsToRequest = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_CONTACTS
    )

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                val viewModel = viewModel<MainViewModel>()
                val dialogQueue = viewModel.visiblePermissionDialogQueue

                val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.onPermissionResult(
                            permission = Manifest.permission.CAMERA,
                            isGranted = isGranted
                        )
                    }
                )

                val readExternalStoragePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.onPermissionResult(
                            permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
                            isGranted = isGranted
                        )
                    }
                )

                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        permissionsToRequest.forEach { permission ->
                            viewModel.onPermissionResult(
                                permission = permission,
                                isGranted = perms[permission] == true
                            )
                        }
                    }
                )

                dialogQueue
                    .reversed()
                    .forEach { permission ->
                        PermissionDialog(
                            permissionTextProvider = when (permission) {
                                Manifest.permission.CAMERA -> {
                                    CameraPermissionTextProvider()
                                }

                                Manifest.permission.ACCESS_MEDIA_LOCATION -> {
                                    ReadExternalStoragePermissionTextProvider()
                                }

                                Manifest.permission.RECORD_AUDIO -> {
                                    RecordAudioPermissionTextProvider()
                                }

                                Manifest.permission.CALL_PHONE -> {
                                    PhoneCallPermissionTextProvider()
                                }

                                else -> return@forEach
                            },
                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                permission
                            ),
                            onDismiss = viewModel::dismissDialog,
                            onOkClick = {
                                viewModel.dismissDialog()
                                multiplePermissionResultLauncher.launch(
                                    arrayOf(permission)
                                )
                            },
                            onGoToAppSettingsClick = ::openAppSettings
                        )
                    }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PerfilScreen(cameraPermissionResultLauncher, readExternalStoragePermissionResultLauncher)
                }
            }
        }
    }

    private fun voltarTelaInicial() {
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PerfilScreen(
        cameraPermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>,
        readExternalStorageermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        var openBottomSheet by rememberSaveable { mutableStateOf(true) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.title_activity_perfil),
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
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 60.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                    IconButton(
                        onClick = {
                            openBottomSheet != openBottomSheet
                        },
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    )
                    {
                        Icon(
                            Icons.Outlined.CameraAlt,
                            contentDescription = "Camera",
                            tint = Color.Black
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    ListItem(
                        headlineContent = { Text(text = "", color = Color.White) },
                        supportingContent = { Text(text = "Teste") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Create,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    ListItem(
                        headlineContent = { Text(text = "Recado", color = Color.White) },
                        supportingContent = { Text(text = "Teste") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Create,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    ListItem(
                        headlineContent = { Text(text = "Email", color = Color.White) },
                        supportingContent = { Text(text = configuracaoFirebase.getFirebaseAutenticacao().currentUser?.email.toString()) },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            }
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Foto do perfil", color = Color.White, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column (
                            modifier = Modifier.padding(end = 25.dp)
                        ) {
                            IconButton(onClick = {
                                cameraPermissionResultLauncher.launch(
                                    Manifest.permission.CAMERA
                                )
                            }) {
                                Icon(imageVector = Icons.Outlined.CameraAlt, contentDescription = null, tint = Color.White)
                            }
                            Text(text = "Câmera", color = Color.White, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                        }
                        Column (
                            modifier = Modifier.padding(end = 25.dp)
                        ) {
                            IconButton(onClick = {
                                readExternalStorageermissionResultLauncher.launch(
                                    Manifest.permission.ACCESS_MEDIA_LOCATION
                                )
                            }) {
                                Icon(imageVector = Icons.Outlined.Image, contentDescription = null, tint = Color.White)
                            }
                            Text(text = "Galeria", color = Color.White, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                        }
                        Column {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Outlined.Person, contentDescription = null, tint = Color.White)
                            }
                            Text(text = "Avatar", color = Color.White, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                        }
                    }
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Preview(showBackground = true)
    @Composable
    fun Perfil() {
        WhatsAppTheme {
            val viewModel = viewModel<MainViewModel>()
            val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    viewModel.onPermissionResult(
                        permission = Manifest.permission.CAMERA,
                        isGranted = isGranted
                    )
                }
            )
            val readExternalStoragePermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    viewModel.onPermissionResult(
                        permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
                        isGranted = isGranted
                    )
                }
            )
            PerfilScreen(cameraPermissionResultLauncher, readExternalStoragePermissionResultLauncher)
        }
    }
}

