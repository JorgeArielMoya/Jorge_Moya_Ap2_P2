package edu.ucne.jorge_moya_ap2_p2.data.remote

import edu.ucne.jorge_moya_ap2_p2.data.remote.dto.GastosRequest
import edu.ucne.jorge_moya_ap2_p2.data.remote.dto.GastosResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GastosApi {
    @GET("api/Gastos")
    suspend fun getGastos () : Response<List<GastosResponse>>

    @GET("api/Gastos/{id}")
    suspend fun getGasto (@Path("id")id : Int) : Response<GastosResponse>

    @POST("api/Gastos")
    suspend fun createGasto (@Body dto: GastosRequest) : Response<GastosResponse>

    @PUT("api/Gastos/{id}")
    suspend fun updateGasto (@Path("id") id : Int, @Body dto: GastosRequest) : Response<GastosResponse>
}