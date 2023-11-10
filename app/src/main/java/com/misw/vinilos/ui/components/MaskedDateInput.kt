package com.misw.vinilos.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaskedDateInput(
    date: String,
    onDateChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier,
    supportingText: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = date,
        onValueChange = { newValue ->
            if (newValue.filter { it.isDigit() }.length <= 8) {
                onDateChange(newValue)
            }
        },
        label = { Text("Release Date") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = DateVisualTransformation(),
        singleLine = true,
        isError = isError,
        modifier = modifier,
        supportingText = supportingText,
        placeholder = { Text("MM/dd/YYYY") }
    )
}

class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val maxLength = 10 // Maximum length of the date string including slashes
        val digitsOnly = text.text.filter { it.isDigit() }
        val trimmed = if (digitsOnly.length > 8) digitsOnly.take(8) else digitsOnly
        // Insert slashes at the appropriate positions
        val formattedText = buildString {
            append(trimmed)
            if (length > 4) insert(4, '/')
            if (length > 2) insert(2, '/')
        }.take(maxLength)
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset in 3..4 -> offset + 1
                    offset > 4 -> offset + 2
                    else -> offset
                }.coerceAtMost(formattedText.length)
            }
            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset in 3..5 -> offset - 1
                    offset > 5 -> offset - 2
                    else -> offset
                }.coerceIn(0, trimmed.length)
            }
        }
        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }
}