@file:OptIn(ExperimentalMaterial3Api::class)

package com.philomath.littlelemon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

class MainActivity : ComponentActivity() {

      private val client = HttpClient(Android) {
            install(ContentNegotiation) {
                  json(contentType = ContentType("json", "plain"))
            }
      }

      private suspend fun getMenuItems() : List<MenuNetworkData> {
            val response : List<MenuNetworkData> = client
                  .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                  .body()
            return response ?: listOf()
      }

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContent {
                  MyNavigation()
//                  LittleLemonTheme {
//                        // A surface container using the 'background' color from the theme
//                        Surface(
//                              modifier = Modifier.fillMaxSize(),
//                              color = MaterialTheme.colorScheme.background
//                        ) {
//                              Greeting("Android")
//                        }
//                  }
            }
      }


      override fun onDestroy() {
            super.onDestroy()
            client.close()
      }
}


@Composable
fun MyNavigation() {
      val context = LocalContext.current
      val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
      val firstName = prefs.getString("firstName", "")
      val secondName = prefs.getString("secondName", "")
      val email = prefs.getString("email", "")
      var startDestination = Onboarding.route
      Log.d("SHARED_PREFS", "$firstName, $secondName, $email")
      if (!firstName.isNullOrEmpty() && !secondName.isNullOrEmpty() && !email.isNullOrEmpty()) {
            startDestination = Home.route
      }

      val navController = rememberNavController()
      NavHost(navController = navController, startDestination = startDestination) {
            composable(Onboarding.route) {
                  OnboardingScreen(navController)
            }
            composable(Home.route) {
                  HomeScreen(navController)
            }
            composable(Profile.route) {
                  ProfileScreen(navController)
            }
      }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//      Text(
//            text = "Hello $name!",
//            modifier = modifier
//      )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//      LittleLemonTheme {
//            Greeting("Android")
//      }
//}

