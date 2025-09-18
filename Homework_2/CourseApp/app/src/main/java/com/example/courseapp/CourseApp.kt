package com.example.courseapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.courseapp.model.Course
import com.example.courseapp.vm.CourseViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseApp(
    viewModel: CourseViewModel = viewModel()
) {
    val courses by viewModel.courses.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Course Viewer & Editor") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Add-only form at the top
            AddCourseForm { dept, num, loc, desc ->
                viewModel.addCourse(dept, num, loc, desc)
            }

            Spacer(Modifier.height(16.dp))
            Text("Courses", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(courses, key = { it.id }) { course ->
                    ExpandableCourseRow(
                        course = course,
                        onDelete = { viewModel.deleteCourse(course.id) },
                        onUpdate = { id, dept, num, loc, desc ->
                            viewModel.updateCourse(id, dept, num, loc, desc)
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun AddCourseForm(
    onAdd: (dept: String, number: String, location: String, description: String) -> Unit
) {
    var dept by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val canSubmit = dept.isNotBlank() && number.isNotBlank() && location.isNotBlank()

    Column {
        OutlinedTextField(
            value = dept,
            onValueChange = { dept = it },
            label = { Text("Department (e.g., CS)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Course Number (e.g., 4530)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (optional)") },
            singleLine = false,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                onAdd(dept, number, location, description)
                dept = ""; number = ""; location = ""; description = ""
            },
            enabled = canSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Course")
        }
    }
}

@Composable
private fun ExpandableCourseRow(
    course: Course,
    onDelete: () -> Unit,
    onUpdate: (UUID, String, String, String, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(course.shortName, style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
            }
        }

        if (expanded) {
            if (editing) {
                EditCourseForm(
                    course = course,
                    onSave = { dept, num, loc, desc ->
                        onUpdate(course.id, dept, num, loc, desc)
                        editing = false
                    },
                    onCancel = { editing = false }
                )
            } else {
                CourseDetail(course, onEdit = { editing = true })
            }
        }
    }
}

@Composable
private fun CourseDetail(course: Course, onEdit: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Department: ${course.dept}")
            Text("Number: ${course.number}")
            Text("Location: ${course.location}")
            if (course.description.isNotBlank()) {
                Text("Description: ${course.description}")
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = onEdit) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
                Spacer(Modifier.width(4.dp))
                Text("Edit")
            }
        }
    }
}

@Composable
private fun EditCourseForm(
    course: Course,
    onSave: (String, String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var dept by remember { mutableStateOf(course.dept) }
    var number by remember { mutableStateOf(course.number) }
    var location by remember { mutableStateOf(course.location) }
    var description by remember { mutableStateOf(course.description) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        OutlinedTextField(
            value = dept,
            onValueChange = { dept = it },
            label = { Text("Department") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Course Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (optional)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = onCancel) { Text("Cancel") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { onSave(dept, number, location, description) }) {
                Text("Save")
            }
        }
    }
}
