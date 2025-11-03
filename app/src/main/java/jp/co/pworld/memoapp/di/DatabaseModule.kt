package jp.co.pworld.memoapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.pworld.memoapp.data.local.dao.MemoDao
import jp.co.pworld.memoapp.data.local.db.MemoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): MemoDatabase =
        Room
            .databaseBuilder(
                app,
                MemoDatabase::class.java,
                "memo_database",
            ).build()

    @Provides
    fun provideMemoDao(db: MemoDatabase): MemoDao = db.memoDao()
}
