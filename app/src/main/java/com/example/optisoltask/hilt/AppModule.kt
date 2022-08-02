package com.example.optisoltask.hilt

import android.content.Context
import com.example.optisoltask.database.db.AppDatabase
import com.example.optisoltask.network.WebServices
import com.example.optisoltask.paging.PagingRepository
import com.example.optisoltask.repository.ApiDataRepository
import com.example.optisoltask.repository.LocalDataRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteTasksDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalTasksDataSource


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    @Singleton
    @Provides
    fun provideHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()


    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit {
        val baseUrl = "https://reqres.in/api/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @RemoteTasksDataSource
    fun provideWebService(retrofit: Retrofit): WebServices =
        retrofit.create(WebServices::class.java)

    @Singleton
    @Provides
    @LocalTasksDataSource
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)!!
    }


}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @AppModule.RemoteTasksDataSource remotesDataSource: WebServices
    ): ApiDataRepository {

        return ApiDataRepository(
            remotesDataSource
        )
    }

    @Singleton
    @Provides
    fun providePagingRepository(
        @AppModule.RemoteTasksDataSource remotesDataSource: WebServices
    ): PagingRepository {

        return PagingRepository(
            remotesDataSource
        )
    }

    @Singleton
    @Provides
    fun provideLocalDataRepository(
        @AppModule.LocalTasksDataSource localDataSource: AppDatabase
    ): LocalDataRepository {
        return LocalDataRepository(
            localDataSource
        )
    }


}


