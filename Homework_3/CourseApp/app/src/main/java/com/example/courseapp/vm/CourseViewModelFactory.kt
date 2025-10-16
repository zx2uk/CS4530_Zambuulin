package com.example.courseapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseapp.data.ICoursesRepository

class CourseViewModelFactory(
    private val repo: ICoursesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            return CourseViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
