package com.example.climaapp

import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         WeatherScreen()
      }

      window.setFlags(
         FLAG_LAYOUT_NO_LIMITS,
         FLAG_LAYOUT_NO_LIMITS
      )

      WindowCompat.setDecorFitsSystemWindows(window, false)
      val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
      windowInsetsController.isAppearanceLightNavigationBars = true
   }
}

@Preview
@Composable
fun WeatherScreen() {
   Box(modifier = Modifier
      .fillMaxSize()
      .background(
         brush = Brush.verticalGradient(
            colors = listOf(
               Color(android.graphics.Color.parseColor("#59469d")),
               Color(android.graphics.Color.parseColor("#643d67"))
            )
         )
      ))
   {
      Column(modifier = Modifier.fillMaxSize()) {
         LazyColumn(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            item {
               Text(text = "Mayormente nublado",
                  fontSize = 20.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  modifier = Modifier
                     .fillMaxSize()
                     .padding(top = 48.dp),
                  textAlign = TextAlign.Center
               )

               Image(painter = painterResource(id = R.drawable.cloudy_sunny),
                  contentDescription = null,
                  modifier = Modifier
                     .size(150.dp)
                     .padding(top = 8.dp)
               )

               // Mostrar fecha y tiempo
               Text(text = "Mar Octubre 8 | 10:00 AM",
                  fontSize = 19.sp,
                  color = Color.White,
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 9.dp),
                  textAlign = TextAlign.Center
               )

               // Mostrar detalles de temperatura
               Text(text = "25°C",
                  fontSize = 63.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 8.dp),
                  textAlign = TextAlign.Center
               )

               Text(text = "H: 27°C  L: 20°%",
                  fontSize = 18.sp,
                  color = Color.White,
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 8.dp),
                  textAlign = TextAlign.Center
               )

               // Caja contiene detalles del tiempo y likes de lluvia, velocidad viento y humedad
               Box(modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 24.dp, vertical = 16.dp)
                  .background(
                     color = colorResource(id = R.color.purple),
                     shape = RoundedCornerShape(20.dp)
                  )
               ) {
                  Row(modifier = Modifier
                     .fillMaxWidth()
                     .height(100.dp)  // altura caja purple (114)
                     .padding(horizontal = 8.dp),  // espacio horizontal
                     verticalAlignment = Alignment.CenterVertically,  // alineacion vertical
                     horizontalArrangement = Arrangement.SpaceBetween  // espacio entre los elementos
                  ) {
                     WeatherDetailItem(icon = R.drawable.rain, value = "22%", label = "Lluvia")
                     WeatherDetailItem(icon = R.drawable.wind, value = "22%", label = "Viento")
                     WeatherDetailItem(icon = R.drawable.humidity, value = "18%", label = "Húmedad")
                  }
               }

               // Mostrando la etiqueta de hoy
               Text(
                  text = "Hoy",
                  fontSize = 20.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 28.dp, vertical = 8.dp),
               )
            }

            // Lista de 7 días, usando un LazyRow
            item {
               LazyRow(modifier = Modifier
                  .fillMaxWidth(),
                  contentPadding = PaddingValues(horizontal = 20.dp),  // espacio horizontal
                  horizontalArrangement = Arrangement.spacedBy(4.dp)  // espacio horizontal entre los elementos
               ) {
                  items(items) { item ->
                     FutureModelViewHolder(item)
                  }
               }
            }

            // Mostrar la etiqueta futuro y los siguientes 7 dias button
            item{
               Row(
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 24.dp, vertical = 16.dp),
                  verticalAlignment = Alignment.CenterVertically,
               ) {
                  Text(text = "Futuro",
                     fontSize = 20.sp,
                     fontWeight = FontWeight.Bold,
                     color = Color.White,
                     modifier = Modifier.weight(1f)
                  )
                  Text(
                     text = "Siguientes 7 días>",
                     fontSize = 16.sp,
                     fontWeight = FontWeight.Bold,
                     color = Color.White,
                  )
               }
            }

            items(dailyItems) { item ->
              FutureItem(item)
            }
         }
      }
   }
}


// Mostrar cada elemento del pronóstico diario futuro
@Composable
fun FutureItem(model: FutureModel) {
   Row(modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically
   ) {
      Text(text = model.day,  // item.day
         color = Color.White,
         fontSize = 14.sp
      )

      Image(painter = painterResource(id = getDrawableResourceId(picPath = model.picPath)),
         contentDescription = null,
         modifier = Modifier
            .padding(start = 32.dp)
            .size(45.dp)
      )
      Text(text = model.status,  // item.status
         modifier = Modifier
            .padding(start = 16.dp)
            .weight(1f),
         color = Color.White,
         fontSize = 14.sp
      )
      Text(text = "${model.highTemp}°C",  // item.highTemp
         modifier = Modifier.padding(end = 16.dp),
         color = Color.White,
         fontSize = 14.sp
      )
      Text(text = "${model.lowTemp}°C",  // item.lowTemp
         color = Color.White,
         fontSize = 14.sp
      )
   }
}

@Composable
fun getDrawableResourceId(picPath: String):Int{
   return when(picPath){
      "Nublado" -> R.drawable.cloudy
      "Soleado" -> R.drawable.sunny
      "Viento" -> R.drawable.wind
      "Lluvia" -> R.drawable.rainy
      "Tormenta" -> R.drawable.storm
      "Nublado_soleado" -> R.drawable.cloudy_sunny
      else -> R.drawable.sunny
   }
}



// datos diarios de muestra
val dailyItems = listOf(
   FutureModel("Lunes", "Nublado", "nublado", 24, 12),
   FutureModel("Martes", "Soleado", "soleado", 25, 13),
   FutureModel("Miércoles", "Viento", "viento", 26, 14),
   FutureModel("Jueves", "Lluvia", "lluvia", 27, 15),
   FutureModel("Viernes", "Tormenta", "tormenta", 28, 16),
   FutureModel("Sábado", "Nublado", "nublado", 29, 17),
   FutureModel("Domingo", "Soleado", "soleado", 30, 18),
)


// marcador de visualización para cada elemento de pronóstico horario
@Composable
fun FutureModelViewHolder(model: HourlyModel) {
   Column(
      modifier = Modifier
         .width(90.dp)
         .wrapContentHeight()
         .padding(4.dp)
         .background(
            color = colorResource(id = R.color.purple),
            shape = RoundedCornerShape(8.dp)
         )
         .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
   ) {
      Text(text = model.hour,
         color = Color.White,
         fontSize = 16.sp,
         modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
         textAlign = TextAlign.Center
      )

      Image(painter = painterResource(id =
         when (model.picPath) {
            "Nublado" -> R.drawable.cloudy
            "Soleado" -> R.drawable.sunny
            "Viento" -> R.drawable.wind
            "Lluvia" -> R.drawable.rainy
            "Tormenta" -> R.drawable.storm
            else -> R.drawable.sunny
         }
      ), contentDescription = null,
         modifier = Modifier
            .size(45.dp)
            .padding(8.dp),
         contentScale = ContentScale.Crop
      )

      Text("${model.temp}°C",
         color = Color.White,
         fontSize = 16.sp,
         modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
         textAlign = TextAlign.Center
      )
   }
}



// Ejemplo de item de la lista de 7 días
val items = listOf(
   HourlyModel("9 am", 28, picPath = "Nublado"),
   HourlyModel("10 am", 29, picPath = "Soleado"),
   HourlyModel("11 am", 30, picPath = "Viento"),
   HourlyModel("12 pm", 31, picPath = "Lluvia"),
   HourlyModel("01 pm", 28, picPath = "Tormenta"),
)


@Composable
fun WeatherDetailItem(icon:Int, value:String, label:String) {
   Column(modifier = Modifier.padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
   ){
      Image(painter = painterResource(id = icon),
         contentDescription = null,
         modifier = Modifier.size(32.dp)
      )
      Text(
         text = value,
         fontWeight = FontWeight.Bold,
         color = colorResource(id = R.color.white),
         textAlign = TextAlign.Center,
         // fontSize = 18.sp,
      )
      Text(
         text = label,
         color = colorResource(id = R.color.white),
         textAlign = TextAlign.Center,
         // fontSize = 12.sp,
      )
   }
}

