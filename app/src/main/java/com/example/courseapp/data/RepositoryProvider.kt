package com.example.courseapp.data

import android.content.Context
import com.example.courseapp.storage.AppDatabase

object RepositoryProvider {
    @Volatile private var repo: ICoursesRepository? = null

    fun provide(appContext: Context): ICoursesRepository =
        repo ?: synchronized(this) {
            repo ?: RoomCoursesRepository(
                AppDatabase.getInstance(appContext).courseDao()
            ).also { repo = it }
        }
}
