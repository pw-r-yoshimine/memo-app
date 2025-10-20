
@file:Suppress("ktlint:standard:package-name")

package jp.co.pworld.memoapp.ui.screen.MemoListScreen

import jp.co.pworld.memoapp.data.local.entity.Memo

/**
 *  メモ一覧 画面の状態を持つ　
 */
data class MemoListUiState(
    val memoList: List<Memo> = emptyList(),
)
