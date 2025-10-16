package com.example.courseapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val dept: String,
    val number: String,
    val location: String,
    val description: String
)
