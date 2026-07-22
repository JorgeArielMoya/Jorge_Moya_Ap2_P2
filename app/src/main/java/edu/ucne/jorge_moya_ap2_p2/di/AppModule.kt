package edu.ucne.jorge_moya_ap2_p2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.jorge_moya_ap2_p2.data.remote.GastosApi
import edu.ucne.jorge_moya_ap2_p2.data.remote.remotedatasource.GastosRemoteDataSource
import edu.ucne.jorge_moya_ap2_p2.data.repository.GastosRepositoryImpl
import edu.ucne.jorge_moya_ap2_p2.domain.repository.GastosRepository
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): GastosApi {
        return Retrofit.Builder()
            .baseUrl("https://api-2026-h7eddqgydxc0fmau.eastus2-01.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GastosApi::class.java)
    }

    @Provides
    @Singleton
    fun providesGastosRepository (api : GastosApi) : GastosRepository{
        return GastosRepositoryImpl(GastosRemoteDataSource(api))
    }
}