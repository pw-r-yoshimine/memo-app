
@file:Suppress(
    "ktlint:standard:package-name",
    "ktlint:standard:function-naming",
)

package jp.co.pworld.memoapp.ui.screen.root

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import jp.co.pworld.memoapp.ui.navigation.AppNavGraph
import jp.co.pworld.memoapp.ui.theme.MemoAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoApp() {
    MemoAppTheme {
        val navController = rememberNavController()
        AppNavGraph(navController = navController)
    }
}
