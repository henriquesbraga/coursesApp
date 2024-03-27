package dev.henriquebraga.myrealmapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.henriquebraga.myrealmapplication.models.Course
import dev.henriquebraga.myrealmapplication.presentation.DataColumn
import dev.henriquebraga.myrealmapplication.presentation.DetailsDialog
import dev.henriquebraga.myrealmapplication.presentation.Fab
import dev.henriquebraga.myrealmapplication.presentation.NewCourseDialog
import dev.henriquebraga.myrealmapplication.presentation.TopBar
import dev.henriquebraga.myrealmapplication.ui.theme.MyRealmApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyRealmApplicationTheme {
                Scaffold(
                    topBar = { TopBar() },
                    floatingActionButton = { Fab { viewModel.showNewCourseDialog() }}
                ) { paddingValues ->
                    val courses by viewModel.courses.collectAsState()

                    DataColumn(courses = courses, paddingValues = paddingValues) {
                        viewModel.showCourseDetails(it)
                    }
                    if (viewModel.courseDetails != null) {
                        DetailsDialog(viewModel = viewModel)
                    }
                    if(viewModel.newCourseDialogVisible) {
                        NewCourseDialog(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

