package com.kheang.firebaseauthenticationloginregister.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kheang.firebaseauthenticationloginregister.data.local.AuthInterceptor
import com.kheang.firebaseauthenticationloginregister.data.local.TokenAuthenticator
import com.kheang.firebaseauthenticationloginregister.data.local.tokenDataStore
import com.kheang.firebaseauthenticationloginregister.data.remote.DummyApi
import com.kheang.firebaseauthenticationloginregister.data.repository.RepositoryProduct
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://dummyjson.com/"

    @Provides
    @Singleton
    fun providerOkHttps(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor (
                authInterceptor
            )
            .authenticator(
                tokenAuthenticator
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    //access Token
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): DataStore<Preferences>{
        return context.tokenDataStore
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit) : DummyApi {
        return retrofit.create(DummyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepositoryProduct(dummyApi: DummyApi) : RepositoryProduct =
        RepositoryProduct(dummyApi)


}