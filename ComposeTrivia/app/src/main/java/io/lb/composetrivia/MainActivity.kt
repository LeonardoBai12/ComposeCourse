package io.lb.composetrivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import io.lb.composetrivia.screen.QuestionViewModel
import io.lb.composetrivia.screen.QuestionScreen
import io.lb.composetrivia.ui.theme.ComposeTriviaTheme
import io.lb.composetrivia.util.AppColors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<QuestionViewModel>()
            TriviaApp(viewModel = viewModel)
        }
    }
}

@Composable
fun TriviaApp(viewModel: QuestionViewModel = hiltViewModel()) {
    ComposeTriviaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColors.mBlack
        ) {
            QuestionScreen(viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TriviaApp()
}