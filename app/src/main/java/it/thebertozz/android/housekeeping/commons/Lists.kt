import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.models.Item

@Composable
fun DetailListTile(item: Item, modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(Modifier.height(4.dp))
        Text(item.name, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Box(Modifier.height(4.dp))
        Text(item.type, fontSize = 16.sp)
        Box(Modifier.height(4.dp))
        if (item.bestBeforeDate.isNotBlank()) {
            Text(item.bestBeforeDate, fontSize = 16.sp)
            Box(Modifier.height(4.dp))
        }
    }
}

@Composable
fun HomeListTile(name: String, description: String, type: String, items: Int, modifier: Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Filled.Description, null, modifier = modifier)
        Box(modifier = Modifier.width(12.dp))
        Column(modifier = modifier.fillMaxWidth()) {
            Box(Modifier.height(4.dp))
            Text(name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Box(Modifier.height(4.dp))
            Row() {
                Text(
                    stringResource(id = R.string.container_description),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.width(4.dp))
                Text(description, fontSize = 16.sp)
            }
            Box(Modifier.height(4.dp))
            Row() {
                Text(
                    stringResource(id = R.string.container_type),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.width(4.dp))
                Text(type, fontSize = 16.sp)
            }
            Box(Modifier.height(4.dp))
            Row() {
                Text(
                    stringResource(id = R.string.container_elements),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.width(4.dp))
                Text(items.toString(), fontSize = 16.sp)
            }
            Box(Modifier.height(8.dp))
        }
//        Box(modifier = Modifier.width(8.dp))
//        Icon(imageVector = Icons.Filled.CameraAlt, null, modifier = modifier)
    }
}