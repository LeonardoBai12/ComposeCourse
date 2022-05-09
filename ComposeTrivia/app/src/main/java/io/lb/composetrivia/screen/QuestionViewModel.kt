    package io.lb.composetrivia.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.composetrivia.data.DataOrException
import io.lb.composetrivia.model.Question
import io.lb.composetrivia.repository.QuestionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val repository: QuestionRepository
): ViewModel() {
    val data = mutableStateOf(
        DataOrException(
            emptyList<Question>(),
            true,
            Exception("")
        )
    )

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value = repository.getTrivia()
        }
    }
}