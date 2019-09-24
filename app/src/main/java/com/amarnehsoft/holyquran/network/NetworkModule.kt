package com.amarnehsoft.holyquran.network

import com.amarnehsoft.holyquran.base.App
import com.amarnehsoft.holyquran.di.Quran
import com.amarnehsoft.holyquran.di.Tafseer
import com.amarnehsoft.holyquran.network.quran.QuranApi
import com.amarnehsoft.holyquran.network.tafseer.TafseerApi
import com.amarnehsoft.holyquran.utils.NetworkUtils

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.connectTimeout(500, TimeUnit.SECONDS)
                .readTimeout(500, TimeUnit.SECONDS).build()
    }

    @Provides
    @Quran
    fun provideRetrofit(httpClient: OkHttpClient, @Quran baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Tafseer
    fun provideTafseerRetrofit(
            httpClient: OkHttpClient,
            @Tafseer baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideAppService(@Quran retrofit: Retrofit): QuranApi {
        return retrofit.create(QuranApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTafseerApi(@Tafseer retrofit: Retrofit): TafseerApi {
        return retrofit.create(TafseerApi::class.java)
    }

    @Provides
    @Quran
    fun provideBaseUrl(): String {
        return ServiceConstants.quranBaseUrl
    }

    @Provides
    @Tafseer
    fun provideBaseUrlForTafseer(): String {
        return ServiceConstants.baseUrlForTafseer
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(app: App): NetworkUtils {
        return NetworkUtils(app)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource = impl
}
