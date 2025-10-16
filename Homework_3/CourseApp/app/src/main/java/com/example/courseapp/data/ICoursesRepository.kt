package com.example.courseapp.data

import com.example.courseapp.model.Course
import kotlinx.coroutines.flow.Flow

interface ICoursesRepository {
    val courses: Flow<List<Course>>
    suspend fun add(dept: String, number: String, location: String, description: String)
    suspend fun update(course: Course)
    suspend fun delete(id: Long)
}
