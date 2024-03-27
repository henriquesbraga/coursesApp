package dev.henriquebraga.myrealmapplication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.henriquebraga.myrealmapplication.MainViewModel

@Composable
fun DetailsDialog(viewModel: MainViewModel) {
    Dialog(
        onDismissRequest = viewModel::hideCourseDetails
    ) {
        Column(
            modifier = Modifier
                .shadow(10.dp)
                .widthIn(200.dp, 200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                .padding(16.dp)

        ) {
            viewModel.courseDetails?.teacher?.address?.let { address ->
                Text(text = address.fullName)
                Text(text = address.street + " " + address.houseNumber)
                Text(text = address.zip.toString() + " " + address.city)
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::deleteCourse,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(text = "Delete")
            }
        }
    }
}