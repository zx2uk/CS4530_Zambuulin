package com.example.courseapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.courseapp.model.Course
import com.example.courseapp.vm.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class) // <-- opt in to TopAppBar
@Composable
fun CourseApp(vm: CourseViewModel) {
    val courses by vm.courses.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Course Viewer & Editor (Room)") }) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            AddOrEditRow(onAdd = { d, n, l, desc -> vm.addCourse(d, n, l, desc) })
            Spacer(Modifier.height(12.dp))
            Text("Courses", fontWeight = FontWeight.Bold)
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            CourseList(
                courses = courses,
                onDelete = { vm.deleteCourse(it.id) }
            )
        }
    }
}

@Composable
private fun AddOrEditRow(
    onAdd: (String, String, String, String) -> Unit
) {
    val (dept, setDept) = remember { mutableStateOf("") }
    val (number, setNumber) = remember { mutableStateOf("") }
    val (location, setLocation) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = dept, onValueChange = setDept,
                label = { Text("Dept") }, modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = number, onValueChange = setNumber,
                label = { Text("Number") }, modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = location, onValueChange = setLocation,
                label = { Text("Location") }, modifier = Modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = description, onValueChange = setDescription,
            label = { Text("Description") }, modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            onAdd(dept, number, location, description)
            setDept(""); setNumber(""); setLocation(""); setDescription("")
        }) {
            Text("Add Course")
        }
    }
}

@Composable
private fun CourseList(
    courses: List<Course>,
    onDelete: (Course) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(courses, key = { it.id }) { c ->
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${c.dept} ${c.number}", fontWeight = FontWeight.SemiBold)
                        Text(c.location)
                    }
                    if (c.description.isNotBlank()) {
                        Spacer(Modifier.height(4.dp))
                        Text(c.description, style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { onDelete(c) }) { Text("Delete") }
                    }
                }
            }
        }
    }
}
