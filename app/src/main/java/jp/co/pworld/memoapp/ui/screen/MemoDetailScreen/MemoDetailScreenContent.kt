
@file:Suppress(
    "ktlint:standard:package-name",
    "ktlint:standard:function-naming",
)

package jp.co.pworld.memoapp.ui.screen.MemoDetailScreenContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * メモ作成・編集　UI表示層
 *
 * @param modifier
 * @param content メモの内容
 */
@Composable
fun MemoDetailScreenContent(
    modifier: Modifier = Modifier,
    content: String = "",
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        modifier =
            modifier
                .fillMaxSize()
                .heightIn(min = 200.dp),
        value = content,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = "メモ",
                color = Color.LightGray,
            )
        },
        singleLine = false,
        maxLines = Int.MAX_VALUE,
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
    )
}

@Preview
@Composable
fun MemoDetailScreenContentPreview() {
    MemoDetailScreenContent(
        modifier = Modifier,
    )
}
