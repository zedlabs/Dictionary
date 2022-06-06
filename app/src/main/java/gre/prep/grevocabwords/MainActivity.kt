package gre.prep.grevocabwords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import gre.prep.grevocabwords.Constants.location_home
import gre.prep.grevocabwords.ui.GroupWordList
import gre.prep.grevocabwords.ui.HomePageWordList
import gre.prep.grevocabwords.ui.WordDetailsPage
import gre.prep.grevocabwords.ui.theme.GreVocabWordsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreVocabWordsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = location_home) {
                    composable("home") { HomePageWordList(navController) }
                    composable("word_list/{groupId}",
                        arguments = listOf(navArgument("groupId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        GroupWordList(
                            navController,
                            backStackEntry.arguments?.getString("groupId") ?: ""
                        )
                    }
                    composable("word_details/{groupNumber}/{wordIdInGroup}") { backStackEntry ->
                        WordDetailsPage(
                            navController,
                            backStackEntry.arguments?.getString("groupNumber") ?: "",
                            backStackEntry.arguments?.getString("wordIdInGroup") ?: ""
                        )
                    }
                }
            }
        }
    }
}