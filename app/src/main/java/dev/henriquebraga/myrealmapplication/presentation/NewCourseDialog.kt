package dev.henriquebraga.myrealmapplication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.henriquebraga.myrealmapplication.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCourseDialog(viewModel: MainViewModel) {

    var courseName by remember { mutableStateOf("")}
    var addressName by remember { mutableStateOf("")}
    var fullName by remember { mutableStateOf("")}
    var cityName by remember { mutableStateOf("")}
    var houseNumber by remember { mutableIntStateOf(0) }
    var zip by remember { mutableIntStateOf(0) }
    var student by remember { mutableStateOf("")}

    val students by viewModel.students.collectAsState()
    val options = students.map { it.name }

    fun setSelectedStudent(studentData: String) {
        student = studentData
    }

    Dialog(
        onDismissRequest = viewModel::hideNewCourseDialog
    ) {
        Column(
            modifier = Modifier
                .shadow(10.dp)
                .widthIn(300.dp, 300.dp)
                .heightIn(100.dp, 550.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {

            OutlinedTextField(
                label = { Text(text = "Course Name")},
                value = courseName,
                onValueChange = { courseName = it }
            )

            Spacer(modifier = Modifier.height(6.dp))

            if(options.isNotEmpty()) {
                if(student == "") student = options.first()
                ExposedSimpleDropdown(
                    options = options,
                    title = "Student"
                ) {
                    setSelectedStudent(it)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                label = { Text(text = "Address Name")},
                value = addressName,
                onValueChange = { addressName = it }
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                label = { Text(text = "Full Name")},
                value = fullName,
                onValueChange = { fullName = it }
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                label = { Text(text = "City Name")},
                value = cityName,
                onValueChange = { cityName = it }
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                label = { Text(text = "House Number")},
                value = houseNumber.toString(),
                onValueChange = { newValue ->
                    houseNumber = newValue.toIntOrNull() ?: 0
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                label = { Text(text = "Zip Code")},
                value = zip.toString(),
                onValueChange = { newValue ->
                    zip = newValue.toIntOrNull() ?: 0
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.createEntry(
                        courseName = courseName,
                        student = student,
                        addressName = addressName,
                        fullname = fullName,
                        cityname = cityName,
                        housenumber = houseNumber,
                        zipcode = zip
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(text = "Create")
            }
        }
    }

}