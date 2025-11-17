
@file:Suppress(
    "ktlint:standard:package-name",
    "ktlint:standard:function-naming",
)

package jp.co.pworld.memoapp.ui.screen.MemoDetailScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import jp.co.pworld.memoapp.ui.navigation.Screen
import jp.co.pworld.memoapp.ui.screen.MemoDetailScreenContent.MemoDetailScreenContent
import jp.co.pworld.memoapp.ui.screen.dialog.DeleteAlertDialog

/**
 * メモ作成・編集　画面制御層
 *
 * @param navController
 * @param viewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoDetailScreen(
    navController: NavHostController,
    viewModel: MemoDetailViewModel = hiltViewModel(),
) {
    val uiState: MemoDetailUiState by viewModel.uiState.collectAsState()

    /**
     * 戻るスワイプ感知
     */
    BackHandler {
        viewModel.processBackNavigation()
        navController.popBackStack()
    }

    Scaffold(
        containerColor = Color.Black,
        contentWindowInsets = WindowInsets.safeDrawing.union(WindowInsets.ime),
        topBar = {
            TopAppBar(
                modifier =
                    Modifier.windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Top + WindowInsetsSides.Horizontal,
                        ),
                    ),
                colors = TopAppBarDefaults.topAppBarColors(Color.Black),
                title = {},
                navigationIcon = {
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            viewModel.processBackNavigation()
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            modifier =
                                Modifier
                                    .padding(5.dp)
                                    .fillMaxSize(),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "メモリスト画面に戻る",
                            tint = Color.White,
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            viewModel.setDeleteDialogVisible(true)
                        },
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .padding(2.dp)
                                    .fillMaxSize()
                                    .background(Color.Gray),
                        ) {
                            Icon(
                                modifier =
                                    Modifier
                                        .padding(5.dp)
                                        .fillMaxSize(),
                                imageVector = Icons.Filled.DeleteOutline,
                                contentDescription = "メモを消すダイアログを出す",
                                tint = Color.White,
                            )
                        }
                    }
                },
            )
        },
        content = { innerPadding ->
            MemoDetailScreenContent(
                modifier =
                    Modifier
                        .padding(innerPadding),
                content = uiState.content,
                onValueChange = {
                    viewModel.onContentChange(it)
                },
            )
        },
    )
    if (uiState.showDeleteDialog) {
        DeleteAlertDialog(
            onDismissRequest = {
                viewModel.setDeleteDialogVisible(false)
            },
            onConfirmation = {
                viewModel.deleteMemo()
                viewModel.setDeleteDialogVisible(false)
                navController.navigate(Screen.MemoList.route) {
                    popUpTo(Screen.MemoList.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
        )
    }
}
