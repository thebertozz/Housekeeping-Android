package it.thebertozz.android.housekeeping.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.ui.theme.HousekeepingTheme

@Composable
fun AlertDialog(confirmClicked: () -> Unit, @StringRes text: Int, dismissClicked: () -> Unit) {
    HousekeepingTheme {
        Column {
            val openDialog = remember { mutableStateOf(true)  }

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    text = {
                        Text(text = stringResource(id = text), fontSize = 16.sp)
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                confirmClicked()
                            }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                            Text(stringResource(id = R.string.confirm))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                dismissClicked()
                            }) {
                            Text(stringResource(id = R.string.dismiss))
                        }
                    }
                )
            }
        }

    }
}