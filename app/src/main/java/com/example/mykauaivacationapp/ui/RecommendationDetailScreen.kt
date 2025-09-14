import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mykauaivacationapp.ui.AppViewModel
import androidx.navigation.NavController // if passing navController for onBack
import com.example.mykauaivacationapp.model.Recommendation


@Composable
fun RecommendationDetailScreen(
    recommendationId: String,
    viewModel: AppViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val recommendation = uiState.categoryList
        .flatMap { it.recommendations }
        .find { it.id == recommendationId }

    var editedDetails by remember { mutableStateOf(recommendation?.details ?: "") }

    if (recommendation == null) {
        Text("Recommendation not found.")
        return
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = recommendation.placeName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Image Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text("Image placeholder")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Editable input
        TextField(
            value = editedDetails,
            onValueChange = { editedDetails = it },
            label = { Text("Details") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Save Button
        Button(
            onClick = {
                viewModel.updateRecommendationDetails(recommendationId, editedDetails)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = recommendation.details ?: "",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
