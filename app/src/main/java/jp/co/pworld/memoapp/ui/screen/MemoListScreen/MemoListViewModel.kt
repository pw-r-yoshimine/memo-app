
@file:Suppress("ktlint:standard:package-name")

package jp.co.pworld.memoapp.ui.screen.MemoListScreen

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
 *  メモリストのViewModel
 */
@HiltViewModel
class MemoListViewModel
    @Inject
    constructor(
        private val repository: MemoRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MemoListUiState())
        val uiState: StateFlow<MemoListUiState> = _uiState

        init {
            fetchMemos()
        }

        // メモ一覧を取得する
        fun fetchMemos() {
            viewModelScope.launch {
                val memoList: List<Memo> = repository.getAll()

                _uiState.update { currentState ->
                    currentState.copy(
                        memoList = memoList,
                    )
                }
            }
        }
    }
