package io.lb.composetrivia.repository

import io.lb.composetrivia.data.DataOrException
import io.lb.composetrivia.model.Question
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

interface QuestionRepository {
    suspend fun getTrivia(): DataOrException<List<Question>, Boolean, Exception>
}