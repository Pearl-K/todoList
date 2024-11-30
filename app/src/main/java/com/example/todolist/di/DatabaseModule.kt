package com.example.todolist.di

import android.content.Context
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // 싱글톤 컴포넌트에서 관리
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)!!
    }

    @Provides
    @Singleton
    fun provideTodoDao(appDatabase: AppDatabase): TodoDao {
        return appDatabase.getTodoDao()
    }
}
