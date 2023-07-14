package it.thebertozz.android.housekeeping.commons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import it.thebertozz.android.housekeeping.R

@Composable
fun EmailTextField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.Blue, cursorColor = Color.Blue),
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(id = R.string.email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
    )
}

@Composable
fun PasswordTextField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon = if (isVisible) painterResource(R.drawable.ic_visibility_on)
    else painterResource(R.drawable.ic_visibility_off)

    val visualTransformation = if (isVisible) VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.Blue, cursorColor = Color.Blue),
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}

@Composable
fun NormalTextField(value: String, onNewValue: (String) -> Unit, @StringRes placeholder: Int, modifier: Modifier = Modifier, leadingIcon: ImageVector, readOnly: Boolean = false) {
    OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.Blue, cursorColor = Color.Blue),
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(id = placeholder)) },
        leadingIcon = { Icon(leadingIcon, contentDescription = null) }
    )
}
