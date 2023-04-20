package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.RepositoryScope
import com.example.myapplication.network.FinHubApi
import com.example.myapplication.network.StockCompanyApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.example.myapplication.model.StockCompany
import com.example.myapplication.model.StockItemListResponseDeserializer
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkApiModule {
    //it provides an instance of the OkHttpClient class
    @Provides
    @Named("finHubApiOkHttpClient")
    fun provideFinHubApiOkHttpClient(): OkHttpClient {
        return newOkHttpClient(accessToken = BuildConfig.FINHUB_API_TOKEN)
    }

    @Provides
    @Named("IexCloudOkHttpClient")
    fun provideIexCloudOkHttpClient(): OkHttpClient {
        return newOkHttpClient(accessToken = BuildConfig.IEX_API_TOKEN)
    }

    //method provides an instance of the FinHubApi interface, use a to
    // serializing deserializing parse the map the Json with specific objects
    @Provides
    @RepositoryScope
    fun provideFinApi(
        @Named("finHubGson") gson: Gson,
        @Named("finHubApiOkHttpClient") client: OkHttpClient): FinHubApi {
        return newFinService(FinHubApi.BASE_URL,client,gson,FinHubApi::class.java)
    }


    @Provides
    @RepositoryScope
    fun provideIexApi(
        @Named("IexCloudOkHttpClient") client: OkHttpClient,
        @Named("IexGson") gson: Gson,
): StockCompanyApi {
        return newIexService(StockCompanyApi.BASE_URL,client,gson,StockCompanyApi::class.java)
    }


    // a helper function to create a Retrofit instance
    private fun newIexService(
        baseUrl: String,
        client: OkHttpClient,
        gson: Gson,
        java: Class<StockCompanyApi>): StockCompanyApi {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
                //serailzi the Json file
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build().create(java)
    }


    private fun newFinService(
        baseUrl: String,
        client: OkHttpClient,
        gson: Gson,
        java: Class<FinHubApi>): FinHubApi {

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build().create(java)

    }


    private fun newOkHttpClient(accessToken: String): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor {
            var request = it.request()
            val url = request.url.newBuilder().addQueryParameter("token", accessToken).build()
            request = request.newBuilder().url(url).build()
            return@addInterceptor it.proceed(request)
        }.addInterceptor(interceptor).build()
    }


    @Provides
    @Named("IexGson")
    fun provideIexGson(): Gson {
        return GsonBuilder().registerTypeAdapter(
            TypeToken.getParameterized(List::class.java, StockCompany::class.java).type,
            StockItemListResponseDeserializer()
        ).create()
    }

    @Provides
    @Named("finHubGson")
    fun provideFinHubGson(): Gson {
        return Gson()
    }
}


