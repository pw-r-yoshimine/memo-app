@file:Suppress(
    "ktlint:standard:package-name",
    "ktlint:standard:function-naming",
)

package jp.co.pworld.memoapp.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import jp.co.pworld.memoapp.ui.screen.MemoDetailScreen.MemoDetailScreen
import jp.co.pworld.memoapp.ui.screen.MemoDetailScreen.MemoType
import jp.co.pworld.memoapp.ui.screen.MemoListScreen.MemoListScreen

/**
 * ナビゲーション
 *
 * @param navController
 */
@Composable
fun AppNavGraph(navController: NavHostController) {
    val slideInFromRight: EnterTransition =
        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(200))

    val slideOutToLeft: ExitTransition =
        slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(200))

    NavHost(
        navController = navController,
        startDestination = Screen.MemoList.route,
        modifier = Modifier.background(Color.Black),
    ) {
        /** メモ一覧画面 */
        composable(
            route = Screen.MemoList.route,
            enterTransition = { slideInFromRight },
            exitTransition = { slideOutToLeft },
        ) {
            MemoListScreen(
                navController = navController,
            )
        }

        /** メモ入力画面 */
        composable(
            route = Screen.MemoDetail.route,
            arguments =
                listOf(
                    navArgument("memoType") {
                        type = NavType.StringType
                    },
                    navArgument("id") {
                        nullable = true
                        type = NavType.StringType
                    },
                ),
            enterTransition = { slideInFromRight },
            exitTransition = { slideOutToLeft },
        ) {
            MemoDetailScreen(
                navController = navController,
            )
        }
    }
}

/**
 * アプリ内の画面遷移先を定義。
 *
 * @param route
 */
sealed class Screen(
    val route: String,
) {
    object MemoList : Screen("memo_list")

    object MemoDetail : Screen("memo_detail/{memoType}?id={id}") {
        fun createRoute(
            memoType: MemoType,
            id: Long? = null,
        ): String =
            if (id == null) {
                "memo_detail/${memoType.routeName}"
            } else {
                "memo_detail/${memoType.routeName}?id=$id"
            }
    }
}
