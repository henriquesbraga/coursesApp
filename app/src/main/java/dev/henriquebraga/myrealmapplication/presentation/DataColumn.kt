package dev.henriquebraga.myrealmapplication.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.henriquebraga.myrealmapplication.models.Course

@Composable
fun DataColumn(courses: List<Course>, paddingValues: PaddingValues, onDeleteClick: (course: Course) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(courses) { course ->
            CourseItem(course = course, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onDeleteClick(course) })
        }
    }
}