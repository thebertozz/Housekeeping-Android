package it.thebertozz.android.housekeeping.permissions

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import it.thebertozz.android.housekeeping.R

@Composable
fun PermissionDialog(@StringRes title: Int, @StringRes text: Int, onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            title = { Text(stringResource(id = title)) },
            text = { Text(stringResource(id = text)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRequestPermission()
                        showWarningDialog = false
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) { Text(text = stringResource(R.string.permissions_request_button)) }
            },
            onDismissRequest = { }
        )
    }
}

@Composable
fun PermissionRationaleDialog(@StringRes title: Int, @StringRes text: Int) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            title = { Text(stringResource(id = title)) },
            text = { Text(stringResource(id = text)) },
            confirmButton = {
                TextButton(
                    onClick = { showWarningDialog = false },
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) { Text("OK") }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}