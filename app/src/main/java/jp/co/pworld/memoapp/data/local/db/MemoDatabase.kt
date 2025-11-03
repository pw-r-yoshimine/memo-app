package jp.co.pworld.memoapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.pworld.memoapp.data.local.dao.MemoDao
import jp.co.pworld.memoapp.data.local.entity.Memo

@Database(entities = [Memo::class], version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
}
