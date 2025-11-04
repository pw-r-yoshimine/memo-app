
@file:Suppress("ktlint:standard:package-name")

package jp.co.pworld.memoapp.ui.screen.MemoDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.pworld.memoapp.data.local.entity.Memo
import jp.co.pworld.memoapp.data.repository.MemoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * メモ編集のviewmodel
 */
@HiltViewModel
class MemoDetailViewModel
    @Inject
    constructor(
        private val repository: MemoRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {

        /**
         * 重要ポイント!!
         * メモ詳細画面のUI状態を保持する
         *  ViewModel内部で状態を更新するために必要
         */
        private val _uiState = MutableStateFlow(MemoDetailUiState())

        /**
         * 重要ポイント!!
         * UIに公開する読み取り専用の状態。
         * UI側はこのuiStateを監視して、状態が更新されるたびに再描画される。
         */
        val uiState: StateFlow<MemoDetailUiState> = _uiState


        private val memoType: MemoType =
            MemoType.fromRouteName(
                savedStateHandle.get<String>("memoType") ?: MemoType.Create.routeName,
            )
        private val id: Long? = savedStateHandle.get<String>("id")?.toLongOrNull()

        /** ViewModel 初期化時、メモの情報を獲得する */
        init {
            setMemoType(memoType)

            // 編集モードである　かつ　メモのidが存在する 場合
            if (memoType == MemoType.Edit && id != null) {
                getMemo(id)
            }
        }

        /**
         * uiSate.memo.setMemoTypeを更新する
         *
         * @param memoType メモのタイプ　新規作成 or 更新
         */
        private fun setMemoType(memoType: MemoType) {
            _uiState.update { currentState ->
                currentState.copy(
                    memoType = memoType,
                )
            }
        }

        /**
         *  メモの情報を獲得する
         *
         *  @param id メモのid
         */
        private fun getMemo(id: Long) {
            viewModelScope.launch {
                _uiState.update { currentState ->
                    currentState.copy(
                        content = repository.getById(id).content,
                    )
                }
            }
        }

        /**
         * uiSate.memo.contentを更新する
         *
         * @param content メモの内容
         * */
        fun onContentChange(content: String) {
            _uiState.update {
                it.copy(content = content)
            }
        }

        /**
         * uiSate.memo.showDeleteDialogを更新する
         *
         * @param isShow true ならダイアログ表示、false なら非表示
         * */
        fun setDeleteDialogVisible(isShow: Boolean) {
            _uiState.update {
                it.copy(
                    showDeleteDialog = isShow,
                )
            }
        }

        /** メモを保存する */
        fun saveMemo() {
            viewModelScope.launch {
                val content: String = _uiState.value.content
                when (memoType) {
                    // メモ作成　
                    MemoType.Create ->
                        repository.insert(
                            Memo(content = content),
                        )
                    // メモ更新　
                    MemoType.Edit ->
                        id?.let { id ->
                            repository.update(
                                Memo(
                                    id = id,
                                    content = content,
                                    updatedAt = System.currentTimeMillis(),
                                ),
                            )
                        }
                }
            }
        }

        /** メモを削除する*/
        fun deleteMemo() {
            if (memoType == MemoType.Create || id == null) return

            viewModelScope.launch {
                repository.delete(
                    Memo(
                        id = id,
                        content = _uiState.value.content,
                    ),
                )
            }
        }
    }
