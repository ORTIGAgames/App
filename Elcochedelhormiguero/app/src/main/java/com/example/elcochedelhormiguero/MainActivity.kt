package com.example.elcochedelhormiguero

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.elcochedelhormiguero.ui.theme.Material3AppTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Material3AppTheme {
                var code by remember {//estado en compose
                    mutableStateOf("")
                }
                val context = LocalContext.current//para cameraProviderFuture
                val lifecycleOwner = LocalLifecycleOwner.current
                val cameraProviderFuture = remember {//Para launchear la camara y renderizalra en el previewView
                    ProcessCameraProvider.getInstance(context)
                }
                var hasCamPermission by remember{//Comprobar los permisos de camara
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(//comprobamos si tenemos el permiso
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                }
                val launcher = rememberLauncherForActivityResult(//hacemos un contrato, decimos que queremos y que resultado queremos
                    contract = ActivityResultContracts.RequestPermission(),//el contrato pedimos permiso
                    onResult = { granted ->//el estado
                        hasCamPermission = granted//actualizamos
                    }
                )
                LaunchedEffect(key1 = true){
                    launcher.launch(Manifest.permission.CAMERA)
                }
                Column(
                    modifier = Modifier.fillMaxSize()//ocupa toda la pantalla
                ) {
                    if(hasCamPermission){
                        AndroidView(
                            factory = {context ->
                                val previewView = PreviewView(context)//con el contexto se puede crear un view para renderizar la camara
                                val preview = Preview.Builder().build()//esto carga el preview nuestro
                                val selector = CameraSelector.Builder()//selecciona la camara trasera
                                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                    .build()
                                preview.setSurfaceProvider(previewView.surfaceProvider)//para confirmar que el view esta usando nuestro preview
                                val imageAnalysis = ImageAnalysis.Builder()//analisis sobre como vamos a analizar la imagen
                                    .setTargetResolution(
                                        Size(//la resolucion que sea la de nuestro preview
                                        previewView.width,
                                        previewView.height
                                    )
                                    )
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)//mantiene el ultimo frame para cuando la funcion no pueda soportar la velocidad de render
                                    .build()
                                imageAnalysis.setAnalyzer(//con las caracteristicas del analisis definido se realiza dicho analisis
                                    ContextCompat.getMainExecutor(context),//el ejecutor
                                    QrCodeAnalyzer{ result ->//el analizador es el analizador de qr creado por nosotros
                                        code = result//el estado en compose
                                    }
                                )
                                try{
                                    cameraProviderFuture.get().bindToLifecycle(
                                        lifecycleOwner,
                                        selector,
                                        preview,
                                        imageAnalysis
                                    )
                                }catch(e: Exception){
                                    e.printStackTrace()
                                }
                                previewView
                            },
                            modifier = Modifier.weight(1f)
                        )
                        Text(//el texto generado en la imagen
                            text = code,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                                .padding(32.dp)
                        )
                    }
                }
            }
        }
    }
}