package com.example.elcochedelhormiguero.scenes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elcochedelhormiguero.Codes.BarCodeQr
import com.example.elcochedelhormiguero.Codes.Qr
import com.example.elcochedelhormiguero.DataStore.Store
import kotlinx.coroutines.launch

@Composable
fun DataSafeScreen() {

    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    // datastore Email
    val dataStore = Store(context)
    // get saved email
    val savedLink = dataStore.getLink.collectAsState(initial = "")

    var link by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            modifier = Modifier
                .padding(16.dp, top = 30.dp),
            text = "Link",
            color = Color.Gray,
            fontSize = 12.sp
        )
        //email field
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = link,
            onValueChange = { link = it },
        )
        Spacer(modifier = Modifier.height(120.dp))
        // save button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 16.dp, end = 16.dp),
            onClick = {
                //launch the class in a coroutine scope
                scope.launch {
                    dataStore.saveLink(link)
                }
            },
        ) {
            // button text
            Text(
                text = "Save",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Text(
            text = savedLink.value!!,
            color = Color.Black,
            fontSize = 18.sp
        )

        if(savedLink.value != ""){
            Qr(BarCodeQr(), savedLink.value!!)
        }
    }
}