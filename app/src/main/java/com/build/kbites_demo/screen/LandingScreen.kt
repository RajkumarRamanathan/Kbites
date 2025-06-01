package com.build.kbites_demo.screen

import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.build.kbites_demo.viewmodel.BakingViewModel
import com.build.kbites_demo.R
import com.build.kbites_demo.UiState

val images = arrayOf(
    // Image generated using Gemini from the prompt "cupcake image"
    R.drawable.ai,
    // Image generated using Gemini from the prompt "cookies images"
    R.drawable.computer,
    // Image generated using Gemini from the prompt "cake images"
    R.drawable.wells,
    // Image generated using Gemini from the prompt "cupcake image"
    R.drawable.wellsfargo,
    // Image generated using Gemini from the prompt "cookies images"
    R.drawable.baked_goods_2,
    // Image generated using Gemini from the prompt "cake images"
    R.drawable.apple_ico,

    // Image generated using Gemini from the prompt "cupcake image"
    R.drawable.weather,
    // Image generated using Gemini from the prompt "cookies images"
    R.drawable.students,
    // Image generated using Gemini from the prompt "cake images"
    R.drawable.police

)
val imageDescriptions = arrayOf(
    "AI",
    "Computer",
    "Wells",
    "WellsFargo",
    "Cookies",
    "Apple",
    "Weather",
    "Students",
    "Police"
)

@Composable
fun BakingScreen(
    bakingViewModel: BakingViewModel = viewModel()
) {
    val selectedImage = remember { mutableIntStateOf(0) }
    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = stringResource(R.string.results_placeholder)
    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
    var result by rememberSaveable { mutableStateOf(placeholderResult) }
    val uiState by bakingViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.baking_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Gallery-style images with two images per row
        val chunkedImages = images.toList().chunked(2)
        val chunkedDescriptions = imageDescriptions.toList().chunked(2)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            chunkedImages.forEachIndexed { rowIndex, imageRow ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    imageRow.forEachIndexed { colIndex, image ->
                        val index = rowIndex * 2 + colIndex
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 12.dp, horizontal = 8.dp)
                        ) {
                            var imageModifier = Modifier
                                .requiredSize(120.dp)
                                .clickable { selectedImage.intValue = index }
                            if (index == selectedImage.intValue) {
                                imageModifier = imageModifier.border(BorderStroke(4.dp, MaterialTheme.colorScheme.primary))
                            }
                            Image(
                                painter = painterResource(image),
                                contentDescription = imageDescriptions[index],
                                modifier = imageModifier
                            )
                            Text(
                                text = imageDescriptions[index],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = when (image) {
                                    R.drawable.ai -> "AI: Artificial intelligence is revolutionizing industries worldwide."
                                    R.drawable.computer -> "Computer: Modern computers power our digital lives."
                                    R.drawable.wells -> "Wells: Financial services are evolving with technology."
                                    R.drawable.wellsfargo -> "WellsFargo: A leader in innovative banking solutions."
                                    R.drawable.baked_goods_2 -> "Cookies: Freshly baked cookies bring joy to every occasion."
                                    R.drawable.apple_ico -> "Apple: Apple products set the standard for design and usability."
                                    R.drawable.weather -> "Weather: Stay updated with the latest weather forecasts."
                                    R.drawable.students -> "Students: Education empowers the next generation."
                                    R.drawable.police -> "Police: Law enforcement ensures community safety."
                                    else -> "Stay tuned for more updates and news from KBites!"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    // If odd number of images, add a Spacer to balance the last row
                    if (imageRow.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

//        Row(
//            modifier = Modifier.padding(all = 16.dp)
//        ) {
//            TextField(
//                value = prompt,
//                label = { Text(stringResource(R.string.label_prompt)) },
//                onValueChange = { prompt = it },
//                modifier = Modifier
//                    .weight(0.8f)
//                    .padding(end = 16.dp)
//                    .align(Alignment.CenterVertically)
//            )
//
//            Button(
//                onClick = {
//                    val bitmap = BitmapFactory.decodeResource(
//                        context.resources,
//                        images[selectedImage.intValue]
//                    )
//                    bakingViewModel.sendPrompt(bitmap, prompt)
//                },
//                enabled = prompt.isNotEmpty(),
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//            ) {
//                Text(text = stringResource(R.string.action_go))
//            }
//        }
//
//        if (uiState is UiState.Loading) {
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//        } else {
//            var textColor = MaterialTheme.colorScheme.onSurface
//            if (uiState is UiState.Error) {
//                textColor = MaterialTheme.colorScheme.error
//                result = (uiState as UiState.Error).errorMessage
//            } else if (uiState is UiState.Success) {
//                textColor = MaterialTheme.colorScheme.onSurface
//                result = (uiState as UiState.Success).outputText
//            }
//            val scrollState = rememberScrollState()
//            Text(
//                text = result,
//                textAlign = TextAlign.Start,
//                color = textColor,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .padding(16.dp)
//                    .fillMaxSize()
//                    .verticalScroll(scrollState)
//            )
//        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun BakingScreenPreview() {
    BakingScreen()
}


