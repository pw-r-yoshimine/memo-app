
@file:Suppress(
    "ktlint:standard:package-name",
    "ktlint:standard:function-naming",
)

package jp.co.pworld.memoapp.ui.screen.MemoListScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.pworld.memoapp.data.local.entity.Memo

/**
 * メモリスト(Top画面) UI表示層　
 *
 * @param modifier
 * @param memoList メモのリスト
 * @param onClick メモのidを受け取る
 */
@Composable
fun MemoListScreenContent(
    modifier: Modifier = Modifier,
    memoList: List<Memo> = emptyList(),
    onClick: (id: Long) -> Unit = {},
) {
    if (memoList.isNotEmpty()) {
        // メモが存在する場合
        val scrollState = rememberScrollState()
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
        ) {
            memoList.forEach { memo ->
                MemoCard(
                    modifier = Modifier,
                    memo = memo.content,
                    onClick = { onClick(memo.id) },
                )
            }
            // メモ追加ボタン分のスペース
            Spacer(modifier = Modifier.size(100.dp))
        }
    } else {
        // メモが存在しない場合
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            EmptyMemoPlaceholder(
                modifier = Modifier,
            )
        }
    }
}

/**
 * メモが１件も存在しない時に表示するデザイン
 *
 * @param modifier
 */
@Composable
private fun EmptyMemoPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(140.dp),
            imageVector = Icons.Filled.NoteAlt,
            tint = Color.White,
            contentDescription = "メモのアイコン",
        )
        Text(
            modifier = Modifier,
            text = "追加したメモはここに表示されます",
            fontSize = 12.sp,
            color = Color.White,
        )
        // メモ追加ボタン分のスペース
        Spacer(modifier = Modifier.size(60.dp))
    }
}

/**
 * メモ一覧に表示する1件分のカード
 *
 * @param modifier
 * @param memo メモの文字列を受け取る
 * @param onClick メモのidを受け取る
 */
@Composable
private fun MemoCard(
    modifier: Modifier = Modifier,
    memo: String = "",
    onClick: () -> Unit,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick() },
        colors =
            CardDefaults.cardColors(
                containerColor = Color.Black,
            ),
        border = BorderStroke(1.dp, Color.Gray),
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            text = memo,
        )
    }
}

/**
 * メモリスト画面 プレビュー
 */
@Preview
@Composable
fun MemoListScreenPreview() {
    MemoListScreenContent(
        modifier = Modifier,
        memoList =
            listOf(
                Memo(
                    id = 1,
                    content = "メモ１",
                    createdAt = System.currentTimeMillis(),
                ),
                Memo(
                    id = 2,
                    content = "メモ２",
                    createdAt = System.currentTimeMillis(),
                ),
            ),
        onClick = {},
    )
}

/**
 * メモが存在しない時のマーク プレビュー
 */
@Preview
@Composable
fun EmptyMemoPlaceholderPreview() {
    EmptyMemoPlaceholder(
        modifier = Modifier,
    )
}

/**
 * メモのカード プレビュー
 */
@Preview
@Composable
fun MemoCardPreview() {
    MemoCard(
        modifier = Modifier,
        memo = "メモだよーーーー",
        onClick = {},
    )
}
