package com.example.elcochedelhormiguero

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

@Composable
fun QrCodeScanner() {
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
            if(code != ""){
                HyperLinkText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    fullText = "Hey Click here to know me better",
                    linkText = listOf("here"),
                    hyperlinks = listOf(code),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }
        }
    }
    return
}

@Composable
fun HyperLinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    linkText: List<String>,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    hyperlinks: List<String>,
    fontSize: TextUnit = TextUnit.Unspecified
){
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}