package com.example.courseapp.model

data class Course(
    val id: Long = 0L,
    val dept: String,
    val number: String,
    val location: String,
    val description: String
)
