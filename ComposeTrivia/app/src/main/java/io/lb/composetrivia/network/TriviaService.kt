package io.lb.composetrivia.network

import io.lb.composetrivia.model.Question
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface TriviaService {
    @GET("world.json")
    suspend fun getTrivia(): List<Question>
}