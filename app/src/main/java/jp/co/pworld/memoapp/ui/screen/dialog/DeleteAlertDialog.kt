
@file:Suppress(
    "ktlint:standard:package-name",
    "ktlint:standard:function-naming",
)

package jp.co.pworld.memoapp.ui.screen.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/**
 * 削除を確認するダイアログ
 *
 * @param modifier
 * @param onDismissRequest いいえボタン
 * @param onConfirmation　はいボタン
 */
@Composable
fun DeleteAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        containerColor = Color.White,
        title = {
            Text(
                text = "本当に削除しますか？",
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Text(
                text = "※２度と復元できません",
                fontSize = 16.sp,
                color = Color.Black,
            )
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
            ) {
                Text(
                    text = "はい",
                    fontSize = 14.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text(
                    text = "いいえ",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
    )
}

@Composable
@Preview
fun DeleteAlertDialogPreview() {
    DeleteAlertDialog(
        modifier = Modifier,
        onDismissRequest = {},
        onConfirmation = {},
    )
}
