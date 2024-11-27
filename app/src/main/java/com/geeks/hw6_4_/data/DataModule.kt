package com.geeks.hw6_4_.data

import com.geeks.hw6_4_.BuildConfig.BASE_URL
import com.geeks.hw6_4_.data.network.CharacterApiService
import com.geeks.hw6_4_.data.network.EpisodeApiService
import com.geeks.hw6_4_.data.repository.character.CharacterRepository
import com.geeks.hw6_4_.data.repository.episode.EpisodeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule: Module = module {
    single { provideOkhttpClient() }

    single { provideRetrofit(get()) }

    single { get<Retrofit>().create(CharacterApiService::class.java) }

    single { get<Retrofit>().create(EpisodeApiService::class.java) }

    single { CharacterRepository(get()) }

    single { EpisodeRepository(get()) }
}

fun provideOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}