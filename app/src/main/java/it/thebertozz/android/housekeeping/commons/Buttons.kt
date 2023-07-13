package it.thebertozz.android.housekeeping.commons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

@Composable
fun SimpleTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    TextButton(onClick = action, modifier = modifier, colors = ButtonDefaults.buttonColors(contentColor = Color.Blue, backgroundColor = Color.Transparent)) {
        Text(text = stringResource(text))
    }
}

@Composable
fun SimpleButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = MaterialTheme.colors.onPrimary
        )
    ) {
        Text(text = stringResource(text), fontSize = 17.sp)
    }
}
