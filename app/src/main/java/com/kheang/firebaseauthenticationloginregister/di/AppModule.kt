package com.kheang.firebaseauthenticationloginregister.di

import com.kheang.firebaseauthenticationloginregister.domain.remote.ProductApi
import com.kheang.firebaseauthenticationloginregister.repository.RepositoryProduct
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit=
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit) : ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    @Singleton
    fun provideRepositoryProduct(productApi: ProductApi) : RepositoryProduct =
        RepositoryProduct(productApi)


}