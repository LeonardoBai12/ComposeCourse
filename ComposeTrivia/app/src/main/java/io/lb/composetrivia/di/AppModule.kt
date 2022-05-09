package io.lb.composetrivia.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.lb.composetrivia.network.TriviaService
import io.lb.composetrivia.repository.QuestionRepository
import io.lb.composetrivia.repository.QuestionRepositoryImpl
import io.lb.composetrivia.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesTriviaService(retrofit: Retrofit): TriviaService {
        return retrofit.create(TriviaService::class.java)
    }

    @Singleton
    @Provides
    fun providesQuestionRepository(service: TriviaService): QuestionRepository {
        return QuestionRepositoryImpl(service)
    }
}