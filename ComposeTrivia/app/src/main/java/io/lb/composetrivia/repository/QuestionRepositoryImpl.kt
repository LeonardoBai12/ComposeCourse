package io.lb.composetrivia.repository

import io.lb.composetrivia.data.DataOrException
import io.lb.composetrivia.model.Question
import io.lb.composetrivia.network.TriviaService

class QuestionRepositoryImpl constructor(
    private val service: TriviaService
): QuestionRepository {
    private val dataOrException = DataOrException<List<Question>, Boolean, Exception>()

    override suspend fun getTrivia(): DataOrException<List<Question>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = service.getTrivia()

            if (dataOrException.data.toString().isNotEmpty())
                dataOrException.loading = false

        } catch (exception: Exception) {
            dataOrException.e = exception
        }
        return dataOrException
    }

    //override suspend fun getTrivia(): Flow<List<Question>> {
    //    return flow {
    //        emit(service.getTrivia())
    //    }.flowOn(Dispatchers.IO)
    //        .conflate()
    //}
}