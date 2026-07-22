package edu.ucne.jorge_moya_ap2_p2.data.repository

import edu.ucne.jorge_moya_ap2_p2.data.remote.Resource
import edu.ucne.jorge_moya_ap2_p2.data.remote.dto.GastosRequest
import edu.ucne.jorge_moya_ap2_p2.data.remote.remotedatasource.GastosRemoteDataSource
import edu.ucne.jorge_moya_ap2_p2.domain.model.Gastos
import edu.ucne.jorge_moya_ap2_p2.domain.repository.GastosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GastosRepositoryImpl @Inject constructor(
    private val gastosRemoteDataSource: GastosRemoteDataSource
) : GastosRepository {
    override fun getGastos(): Flow<Resource<List<Gastos>>> = flow{
        emit(Resource.Loading())
        gastosRemoteDataSource.getGastos()
            .onSuccess { emit(Resource.Success(it.map { dto -> dto.toDomain() })) }
            .onFailure { emit(Resource.Error(it.message?: "Error desconocido")) }
    }

    override fun getGasto(id: Int): Flow<Resource<Gastos>> = flow{
        emit(Resource.Loading())
        gastosRemoteDataSource.getGasto(id)
            .onSuccess { emit(Resource.Success(it.toDomain())) }
            .onFailure { emit(Resource.Error(it.message?: "Error desconocido")) }
    }

    override fun createGasto(
        fecha: String,
        suplidor: String,
        ncf: String,
        itbis: Double,
        monto: Double
    ): Flow<Resource<Gastos>> = flow{
        emit(Resource.Loading())
        gastosRemoteDataSource.createGasto(GastosRequest(fecha, suplidor, ncf, itbis, monto))
            .onSuccess { emit(Resource.Success(it.toDomain())) }
            .onFailure { emit(Resource.Error(it.message?: "Error desconocido")) }
    }

    override fun updateGasto(
        id: Int,
        fecha: String,
        suplidor: String,
        ncf: String,
        itbis: Double,
        monto: Double
    ): Flow<Resource<Gastos>> = flow{
        emit(Resource.Loading())
        gastosRemoteDataSource.updateGasto(id, GastosRequest(fecha,suplidor, ncf, itbis, monto))
            .onSuccess { emit(Resource.Success(it.toDomain())) }
            .onFailure { emit(Resource.Error(it.message?: "Error desconocido")) }
    }
}