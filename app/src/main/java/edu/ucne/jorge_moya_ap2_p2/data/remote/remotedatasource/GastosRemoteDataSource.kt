package edu.ucne.jorge_moya_ap2_p2.data.remote.remotedatasource

import edu.ucne.jorge_moya_ap2_p2.data.remote.GastosApi
import edu.ucne.jorge_moya_ap2_p2.data.remote.dto.GastosRequest
import edu.ucne.jorge_moya_ap2_p2.data.remote.dto.GastosResponse
import javax.inject.Inject

class GastosRemoteDataSource @Inject constructor(
    private val api : GastosApi
) {
    suspend fun getGastos () : Result<List<GastosResponse>>{
        return try {
            val response = api.getGastos()
            if (!response.isSuccessful){
                Result.failure(Exception("Error de red ${response.message()}"))
            }
            else{
                Result.success(response.body()!!)
            }
        }catch (e : Exception){
            Result.failure(Exception(e.message ?: "Error desconocido"))
        }
    }

    suspend fun getGasto(id : Int) : Result<GastosResponse>{
        return try {
            val response = api.getGasto(id)
            if (!response.isSuccessful){
                Result.failure(Exception("Error de red ${response.message()}"))
            }
            else{
                Result.success(response.body()!!)
            }
        }catch (e : Exception){
            Result.failure(Exception(e.message ?: "Error desconocido"))
        }
    }

    suspend fun createGasto (dto : GastosRequest) : Result<GastosResponse>{
        return try {
            val response = api.createGasto(dto)
            if (!response.isSuccessful){
                Result.failure(Exception("Error de red ${response.message()}"))
            }
            else{
                Result.success(response.body()!!)
            }
        }catch (e : Exception){
            Result.failure(Exception(e.message ?: "Error desconocido"))
        }

    }

    suspend fun updateGasto (id : Int, dto: GastosRequest) : Result<GastosResponse>{
        return try {
            val response = api.updateGasto(id, dto)
            if (!response.isSuccessful){
                Result.failure(Exception("Error de red ${response.message()}"))
            }
            else{
                Result.success(GastosResponse(id, dto.fecha, dto.suplidor, dto.ncf, dto.itbis, dto.monto))
            }
        }catch (e : Exception){
            Result.failure(Exception(e.message?: "Error desconocido"))
        }
    }
}