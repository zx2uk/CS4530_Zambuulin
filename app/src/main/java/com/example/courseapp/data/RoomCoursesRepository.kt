package com.example.courseapp.data

import com.example.courseapp.model.Course
import com.example.courseapp.storage.CourseDao
import com.example.courseapp.storage.CourseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomCoursesRepository(private val dao: CourseDao) : ICoursesRepository {

    override val courses: Flow<List<Course>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun add(dept: String, number: String, location: String, description: String) {
        dao.insert(CourseEntity(dept = dept, number = number, location = location, description = description))
    }

    override suspend fun update(course: Course) {
        dao.update(course.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.deleteById(id)
    }
}

private fun CourseEntity.toDomain() = Course(
    id = id, dept = dept, number = number, location = location, description = description
)

private fun Course.toEntity() = CourseEntity(
    id = id, dept = dept, number = number, location = location, description = description
)
