package com.example.courseapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.data.ICoursesRepository
import com.example.courseapp.model.Course
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val repo: ICoursesRepository) : ViewModel() {

    val courses: StateFlow<List<Course>> = repo.courses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addCourse(dept: String, number: String, location: String, description: String) {
        viewModelScope.launch {
            if (dept.isNotBlank() && number.isNotBlank() && location.isNotBlank()) {
                repo.add(dept.trim(), number.trim(), location.trim(), description.trim())
            }
        }
    }

    fun deleteCourse(id: Long) {
        viewModelScope.launch { repo.delete(id) }
    }

    fun updateCourse(id: Long, dept: String, number: String, location: String, description: String) {
        viewModelScope.launch {
            repo.update(
                Course(
                    id = id,
                    dept = dept.trim(),
                    number = number.trim(),
                    location = location.trim(),
                    description = description.trim()
                )
            )
        }
    }
}
