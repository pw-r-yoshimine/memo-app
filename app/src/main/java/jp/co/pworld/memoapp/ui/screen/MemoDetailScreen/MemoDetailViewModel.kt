
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
 * メモ編集用の ViewModel
 *
 * @param repository メモデータのリポジトリ
 * @param savedStateHandle SavedStateHandle（画面状態保持用）
 */
@HiltViewModel
class MemoDetailViewModel
    @Inject
    constructor(
        private val repository: MemoRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MemoDetailUiState())
        val uiState: StateFlow<MemoDetailUiState> = _uiState

        private val memoType: MemoType =
            MemoType.fromRouteName(
                savedStateHandle.get<String>("memoType") ?: MemoType.Create.routeName,
            )
        private val id: Long? = savedStateHandle.get<String>("id")?.toLongOrNull()

        init {
            initializeState()
        }

        /**
         * 戻る操作が発生した際に、現在のメモ内容に応じて
         * 保存または削除の処理を行う。
         */
        fun processBackNavigation() {
            val state = _uiState.value

            when {
                // 編集モードでかつ、メモの内容が空の場合 -> メモを削除する
                state.memoType == MemoType.Edit && state.content.isEmpty() -> {
                    deleteMemo()
                }
                // メモが記入されている場合 -> メモを保存する
                state.content.isNotBlank() -> {
                    saveMemo()
                }
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

        /**
         * 初期状態の設定を行う。
         *
         * メモ作成・編集画面の初期化処理として呼ばれる。
         * - memoType を UI state に反映
         * - 編集モードでメモ ID が存在する場合、既存のメモ内容を取得
         */
        private fun initializeState() {
            setMemoType(memoType)

            if (memoType != MemoType.Edit || id == null) return

            getMemo(id)
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
    }
