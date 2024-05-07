package no.uio.ifi.in2000.testgit.ui.home.Dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NameField(
    title: String,
    placeholder: String,
    text: String,
    onValueChange: (String) -> Unit,
    error : Boolean,
    errorMessage : String,
) {
    TextField(
        value = text,
        singleLine = true,
        onValueChange = onValueChange,
        label = {
            Text(text = title)
        },
        placeholder = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Pin")
        },
        isError = error,
        supportingText = {
            if (error) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (error)
                Icon(
                    Icons.Filled.Clear,"error",
                    tint = MaterialTheme.colorScheme.error)
        },
    )
}

@Composable
fun DoubleTextField(
    title: String,
    placeholder: String,
    text: String,
    onValueChange: (String) -> Unit,
    error : Boolean,
    errorMessage : String,

    ) {
    TextField(
        value = text,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Pin")
        },
        onValueChange = onValueChange,
        label = {
            Text(text = title)
        },
        placeholder = {
            Text(text = placeholder)
        },
        isError = error,
        supportingText = {
            if (error) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (error)
                Icon(
                    Icons.Filled.Clear,"error",
                    tint = MaterialTheme.colorScheme.error)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )

}

fun validateCoordinates(cord : String) : Boolean {
    val _cord = cord.toDoubleOrNull()
    return when {
        _cord == null -> true
        _cord < 0.0 -> true
        _cord > 360.0 -> true
        else ->
            false
    }
}
