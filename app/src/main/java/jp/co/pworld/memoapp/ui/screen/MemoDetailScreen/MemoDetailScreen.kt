
@file:Suppress(
    "ktlint:standard:package-name",
    "ktlint:standard:function-naming",
)

package jp.co.pworld.memoapp.ui.screen.MemoDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
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
                        onClick = {
                            when {
                                // 編集モードでかつ、メモの内容が空の場合 -> メモを削除する
                                uiState.memoType == MemoType.Edit && uiState.content.isEmpty() -> {
                                    viewModel.deleteMemo()
                                }
                                // メモが記入されている場合 -> メモを保存する
                                uiState.content.isNotBlank() -> {
                                    viewModel.saveMemo()
                                }
                            }
                            navController.navigate(Screen.MemoList.route) {
                                popUpTo(Screen.MemoList.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "メモリスト画面に戻る",
                            tint = Color.White,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.setDeleteDialogVisible(true)
                        },
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .background(color = Color.Gray)
                                    .padding(4.dp),
                        ) {
                            Icon(
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
