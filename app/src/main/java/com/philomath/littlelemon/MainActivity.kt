@file:OptIn(ExperimentalMaterial3Api::class)

package com.philomath.littlelemon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
      //      private val menuItems = MutableLiveData<List<MenuItemNetwork>>()
      val TAG: String = "MAIN_ACTIVITY"
      private val MENU_URL =
            "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"


      private val client = HttpClient(Android) {
            install(ContentNegotiation) {
                  json(contentType = ContentType("text", "plain"))
            }
      }


      private suspend fun getMenuItems() {
            val response: MenuNetworkData = client
                  .get(MENU_URL)
                  .body()

            Log.d("${TAG}_RESPONSE", "${response.menuItems}")

            val menuItems = response.menuItems.map { it ->
                  MenuItem(
                        id = it.id,
                        title = it.title,
                        description = it.description,
                        price = it.price,
                        image = it.image,
                        category = it.category,
                  )
            }

            Log.d("${TAG}_PARSE", "$menuItems")

            val db = AppDatabase.invoke(applicationContext)
            db.menuItemDao().insertMenuItems(menuItems)
      }

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val prefs = applicationContext.getSharedPreferences(
                  application.packageName,
                  Context.MODE_PRIVATE
            )

            val firstName = prefs.getString("firstName", "")
            val secondName = prefs.getString("secondName", "")
            val email = prefs.getString("email", "")
            var isFirstLogin = true
            Log.d("SHARED_PREFS", "$firstName, $secondName, $email")
            if (!firstName.isNullOrEmpty() && !secondName.isNullOrEmpty() && !email.isNullOrEmpty()) {
                  isFirstLogin = false
            }

            if (isFirstLogin) {
                  lifecycleScope.launch(Dispatchers.IO) {
                        try {
                              getMenuItems()
                        } catch (e: Exception) {
                              Log.d("${TAG}_ERROR", "$e");
                        }
                  }
            }

            setContent {
                  MyNavigation(isFirstLogin)
            }
      }


      override fun onDestroy() {
            super.onDestroy()
            client.close()
      }
}


@Composable
fun MyNavigation(isFirstLogin: Boolean) {
      var startDestination = Onboarding.route
      if (!isFirstLogin) {
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


