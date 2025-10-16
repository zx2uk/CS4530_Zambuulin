package com.example.courseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.courseapp.ui.theme.CourseAppTheme
import com.example.courseapp.vm.CourseViewModel
import com.example.courseapp.vm.CourseViewModelFactory
import com.example.courseapp.data.RepositoryProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = RepositoryProvider.provide(applicationContext)
        val vm = ViewModelProvider(this, CourseViewModelFactory(repo))
            .get(CourseViewModel::class.java)

        setContent {
            CourseAppTheme {
                CourseApp(vm)
            }
        }
    }
}
