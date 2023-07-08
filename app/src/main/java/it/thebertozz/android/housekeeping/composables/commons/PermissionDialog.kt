package it.thebertozz.android.housekeeping.composables.commons

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
fun PermissionDialog(onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            title = { Text(stringResource(id = R.string.notification_permission_title)) },
            text = { Text(stringResource(id = R.string.notification_permission_rationale)) },
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
                ) { Text(text = stringResource(R.string.notification_permissions_button)) }
            },
            onDismissRequest = { }
        )
    }
}

@Composable
fun PermissionRationaleDialog() {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            title = { Text(stringResource(id = R.string.notification_permission_title)) },
            text = { Text(stringResource(id = R.string.notification_permission_rationale)) },
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