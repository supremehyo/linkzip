
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.linkzip.linkzip.ui.theme.LinkZipTheme

@Composable
fun CustomSnackbar(
    message: String,
    icon: Int,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        snackbarHostState.showSnackbar(
            message = message,
            duration = duration
        )
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
    ) { innerPadding ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(LinkZipTheme.color.wg70)
                .clip(RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "toastIcon",
                modifier = Modifier
                    .padding(start = 14.dp)
            )
            Text(
                text = message,
                style = LinkZipTheme.typography.medium12.copy(
                    color = LinkZipTheme.color.white
                )
            )
        }
    }
}