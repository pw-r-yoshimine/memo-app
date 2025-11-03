
@file:Suppress("ktlint:standard:package-name")

package jp.co.pworld.memoapp.ui.screen.MemoDetailScreen

/**
 * メモのタイプを定義
 *
 * @param routeName
 */
sealed class MemoType(
    val routeName: String,
) {
    object Create : MemoType("create")

    object Edit : MemoType("edit")

    companion object {
        fun fromRouteName(name: String): MemoType =
            when (name) {
                Create.routeName -> Create
                Edit.routeName -> Edit
                else -> Create
            }
    }
}
