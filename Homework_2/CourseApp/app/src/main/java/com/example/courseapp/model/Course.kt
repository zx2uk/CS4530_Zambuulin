package com.example.courseapp.model

import java.util.UUID

data class Course(
    val id: UUID = UUID.randomUUID(),
    val dept: String,
    val number: String,
    val location: String,
    val description: String = ""   // optional
) {
    val shortName: String get() = "${dept.uppercase()} ${number.trim()}"
}
