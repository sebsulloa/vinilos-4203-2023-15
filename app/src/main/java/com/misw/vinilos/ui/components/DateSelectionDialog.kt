package com.misw.vinilos.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectionDialog(
    showDialog: MutableState<Boolean>,
    onDateSelected: (Long?) -> Unit,
) {
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    val confirmEnabled = remember(datePickerState) {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    if (showDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onDateSelected(datePickerState.selectedDateMillis)
                    },
                    enabled = confirmEnabled.value,
                    modifier = Modifier.testTag("confirm_button")
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                    },
                    modifier = Modifier.testTag("dismiss_button")
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}