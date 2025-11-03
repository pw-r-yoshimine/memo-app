package jp.co.pworld.memoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.co.pworld.memoapp.data.local.entity.Memo

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo ORDER BY id DESC")
    suspend fun getAll(): List<Memo>

    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun getById(id: Long): Memo

    @Insert
    suspend fun insert(memo: Memo)

    @Update
    suspend fun update(memo: Memo)

    @Delete
    suspend fun delete(memo: Memo)
}
