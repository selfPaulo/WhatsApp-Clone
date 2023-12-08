package com.example.whatsapp.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.canhub.cropper.CropImage.CancelledResult.uriContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.whatsapp.R
import com.example.whatsapp.activity.ui.theme.WhatsAppTheme
import com.example.whatsapp.config.ConfiguracaoFirebase
import com.example.whatsapp.helper.AccessMediaLocationPermissionTextProvider
import com.example.whatsapp.helper.CameraPermissionTextProvider
import com.example.whatsapp.helper.MainViewModel
import com.example.whatsapp.helper.PermissionDialog
import com.example.whatsapp.helper.PhoneCallPermissionTextProvider
import com.example.whatsapp.helper.RecordAudioPermissionTextProvider
import com.example.whatsapp.helper.UsuarioFirebase
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

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
    private val storageReference = configuracaoFirebase.getFirebaseStorage()
    private val usuarioFirebase = UsuarioFirebase()
    private val idUsuario = UsuarioFirebase().getIdentificadorUsuario()
    private lateinit var imageRef : StorageReference
    private lateinit var uploadTask: UploadTask

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

                val accessMediaLocationPermissionResultLauncher = rememberLauncherForActivityResult(
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
                                    AccessMediaLocationPermissionTextProvider()
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
                    PerfilScreen(
                        cameraPermissionResultLauncher,
                        accessMediaLocationPermissionResultLauncher
                    )
                }
            }
        }
    }

    private fun voltarTelaInicial() {
        finish()
    }

    private fun atualizarFotoUsuario(uri: Uri) {
        usuarioFirebase.atualizarFotoUsuario(uri)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    @Composable
    fun PerfilScreen(
        cameraPermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>,
        accessMediaLocationPermissionResultLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }

        val cameraPermission = rememberPermissionState(
            permission = Manifest.permission.CAMERA
        )

        val accessMediaLocationPermission = rememberPermissionState(
            permission = Manifest.permission.ACCESS_MEDIA_LOCATION
        )

        val usuarioAtual = usuarioFirebase.getUsuarioAtual()

        val context = LocalContext.current
        var imagemPerfilAtual by remember { mutableStateOf<Bitmap?>(null) }
        var imagemPerfilDB by remember { mutableStateOf<Uri?>(null) }
        if (usuarioAtual != null) {
            imagemPerfilDB = usuarioAtual.photoUrl
        }
        val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                imagemPerfilDB = result.uriContent
            }
        }
        if (imagemPerfilDB != null) {
            imagemPerfilAtual = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, imagemPerfilDB)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, imagemPerfilDB!!)
                ImageDecoder.decodeBitmap(source)
            }
        }

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
                        .padding(top = 80.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box {
                        if (imagemPerfilAtual != null) {
                            Image(
                                bitmap = imagemPerfilAtual?.asImageBitmap()!!,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.Blue)
                                    .size(200.dp)
                            )

                            val baos = ByteArrayOutputStream()
                            imagemPerfilAtual!!.compress(Bitmap.CompressFormat.JPEG, 70, baos)
                            val dadosImagem = baos.toByteArray()

                            imageRef = storageReference
                                .child("imagens")
                                .child("perfil")
                                .child("$idUsuario.jpeg")

                            val uri = imageRef.downloadUrl

                            atualizarFotoUsuario(uri.result)

                            uploadTask = imageRef.putBytes(dadosImagem)

                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.no_picture),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                                    .size(200.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                openBottomSheet = !openBottomSheet
                            },
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .align(alignment = Alignment.BottomEnd)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                        )
                        {
                            Image(
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .size(50.dp)
                                    .padding(10.dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = "Teste",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        overlineContent = {
                            Text(text = "Nome")
                        },
                        supportingContent = { Text(text = "Esse não é seu nome de usuário nem seu PIN. Esse nome será exibido para seus contatos do WhatsApp") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
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
                        headlineContent = { Text(text = "Teste", color = Color.White) },
                        overlineContent = { Text(text = "Recado") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
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
                        headlineContent = { Text(text = configuracaoFirebase.getFirebaseAutenticacao().currentUser?.email.toString(), color = Color.White) },
                        overlineContent = { Text(text = "Email", color = Color.White) },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Call,
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
                    ListItem(
                        headlineContent = {
                            Text(
                                "Foto do perfil",
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.RestoreFromTrash,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(end = 25.dp)
                        ) {
                            IconButton(onClick = {
                                if (cameraPermission.status.isGranted) {
                                    val cropOption =
                                        CropImageContractOptions(uriContent, CropImageOptions())
                                    imageCropLauncher.launch(cropOption)
                                } else {
                                    cameraPermissionResultLauncher.launch(
                                        Manifest.permission.CAMERA
                                    )
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.CameraAlt,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = "Câmera",
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }
                        Column(
                            modifier = Modifier.padding(end = 25.dp)
                        ) {
                            IconButton(onClick = {
                                if (accessMediaLocationPermission.status.isGranted) {
                                    val cropOption =
                                        CropImageContractOptions(uriContent, CropImageOptions())
                                    imageCropLauncher.launch(cropOption)
                                } else {
                                    accessMediaLocationPermissionResultLauncher.launch(
                                        Manifest.permission.ACCESS_MEDIA_LOCATION
                                    )
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Image,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = "Galeria",
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }
                        Column {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Filled.Face,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = "Avatar",
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
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
            val accessMediaLocationPermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    viewModel.onPermissionResult(
                        permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
                        isGranted = isGranted
                    )
                }
            )
            PerfilScreen(
                cameraPermissionResultLauncher,
                accessMediaLocationPermissionResultLauncher
            )
        }
    }
}


