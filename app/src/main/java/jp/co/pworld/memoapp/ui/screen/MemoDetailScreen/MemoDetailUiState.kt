
@file:Suppress("ktlint:standard:package-name")

package jp.co.pworld.memoapp.ui.screen.MemoDetailScreen

/**
 * メモ作成・編集 画面の状態を持つ　
 */
data class MemoDetailUiState(
    val memoType: MemoType = MemoType.Create,
    val content: String = "",
    val showDeleteDialog: Boolean = false,
)
