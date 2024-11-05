package com.philomath.littlelemon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
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
                  HomeScreen()
            }
            composable(Profile.route) {
                  ProfileScreen()
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

