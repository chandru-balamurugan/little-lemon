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
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
      val TAG: String = "MAIN_ACTIVITY"

      private val client = HttpClient(Android) {
            install(ContentNegotiation) {
                  json(contentType = ContentType("json", "plain"))
//                  json(Json{ ignoreUnknownKeys = true})
            }
      }

      private suspend fun getMenuItems() {
            val response: MenuNetworkData = client
                  .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                  .body()

            Log.d(TAG, "$response")

//            val menuItems = response.map { it ->
//                  MenuItem(
//                        id = it.id,
//                        name = it.title,  // Map title to name for the entity
//                        description = it.description,
//                        price = it.price,
//                        image = it.image,
//                        category = it.category,
//                  )
//            }
//
//            val db = AppDatabase.invoke(applicationContext)
//            db.menuItemDao().insertMenuItems(menuItems)
      }

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            lifecycleScope.launch(Dispatchers.IO) {
                  try {
                        getMenuItems()
                  } catch (e: Exception) {
                        Log.d(TAG, "$e");
                  }
            }

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

