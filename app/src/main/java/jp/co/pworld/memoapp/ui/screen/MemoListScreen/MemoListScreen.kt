
@file:Suppress(
    "ktlint:standard:package-name",
    "ktlint:standard:function-naming",
)

package jp.co.pworld.memoapp.ui.screen.MemoListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import jp.co.pworld.memoapp.ui.navigation.Screen
import jp.co.pworld.memoapp.ui.screen.MemoDetailScreen.MemoType

/**
 * メモリスト(Top画面) 画面制御層
 *
 * @param navController
 * @param viewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoListScreen(
    navController: NavController,
    viewModel: MemoListViewModel = hiltViewModel(),
) {
    val uiState: MemoListUiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier,
        containerColor = Color(0xFF3A3A3A),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CenterAlignedTopAppBar(
                modifier =
                    Modifier.windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Top + WindowInsetsSides.Horizontal,
                        ),
                    ),
                title = {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        color = Color.White,
                        text = "メモ帳",
                    )
                },
                colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF3A3A3A),
                    ),
            )
        },
        content = { innerPadding ->
            MemoListScreenContent(
                modifier =
                    Modifier
                        .padding(innerPadding),
                memoList = uiState.memoList,
                onClick = { id ->
                    navController.navigate(Screen.MemoDetail.createRoute(MemoType.Edit, id = id))
                },
            )
        },
        floatingActionButton = {
            Box(
                modifier =
                    Modifier.size(60.dp),
            ) {
                MemoAddButton(
                    modifier = Modifier.fillMaxSize(),
                    onClick = {
                        navController.navigate(Screen.MemoDetail.createRoute(MemoType.Create))
                    },
                )
            }
        },
    )
}

/**
 * メモを追加するボタン
 *
 * @param modifier
 * @param onClick
 */
@Composable
private fun MemoAddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier,
        onClick = { onClick() },
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFF81C784),
            ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            tint = Color.Black,
            contentDescription = "収支入力",
            modifier = Modifier.fillMaxSize().padding(16.dp),
        )
    }
}
