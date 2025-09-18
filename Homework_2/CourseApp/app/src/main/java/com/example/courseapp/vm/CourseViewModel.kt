package com.example.courseapp.vm

import androidx.lifecycle.ViewModel
import com.example.courseapp.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class CourseViewModel : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    /**
     * Add a new course
     */
    fun addCourse(dept: String, number: String, location: String, description: String) {
        val newCourse = Course(
            dept = dept.trim(),
            number = number.trim(),
            location = location.trim(),
            description = description.trim()
        )
        _courses.update { it + newCourse }
    }

    /**
     * Delete a course by ID
     */
    fun deleteCourse(id: UUID) {
        _courses.update { old -> old.filterNot { it.id == id } }
    }

    /**
     * Update an existing course
     */
    fun updateCourse(id: UUID, dept: String, number: String, location: String, description: String) {
        _courses.update { list ->
            list.map { course ->
                if (course.id == id) {
                    course.copy(
                        dept = dept.trim(),
                        number = number.trim(),
                        location = location.trim(),
                        description = description.trim()
                    )
                } else course
            }
        }
    }
}
