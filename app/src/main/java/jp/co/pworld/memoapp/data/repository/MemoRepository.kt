package jp.co.pworld.memoapp.data.repository

import jp.co.pworld.memoapp.data.local.dao.MemoDao
import jp.co.pworld.memoapp.data.local.entity.Memo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * メモ　作成、編集、削除 のレポジトリ　
 */
@Singleton
class MemoRepository
    @Inject
    constructor(
        private val memoDao: MemoDao,
    ) {
        suspend fun getAll() = memoDao.getAll()

        suspend fun getById(id: Long) = memoDao.getById(id)

        suspend fun insert(memo: Memo) = memoDao.insert(memo)

        suspend fun update(memo: Memo) = memoDao.update(memo)

        suspend fun delete(memo: Memo) = memoDao.delete(memo)
    }
