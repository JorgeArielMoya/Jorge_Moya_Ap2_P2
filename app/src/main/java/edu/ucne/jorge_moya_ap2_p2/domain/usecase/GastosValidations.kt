package edu.ucne.jorge_moya_ap2_p2.domain.usecase

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class GastosValidations(
    val isValid : Boolean,
    val error : String? = null
)

fun validateFecha (fecha : String) : GastosValidations{
    return when{
        fecha.isBlank() -> GastosValidations(false, "Fecha requerida")
        else -> GastosValidations(true)
    }
}

fun validateSuplidor (suplidor : String) : GastosValidations{
    return when{
        suplidor.isBlank() -> GastosValidations(false, "Suplidor requerido")
        else -> GastosValidations(true)
    }
}

fun validateNcf (ncf : String) : GastosValidations{
    return when{
        ncf.isBlank() -> GastosValidations(false, "Ncf requerido")
        else -> GastosValidations(true)
    }
}

fun validateItbis (itbis : String) : GastosValidations{
    return when{
        itbis.isBlank() -> GastosValidations(false, "Itbis requerido")
        else -> GastosValidations(true)
    }
}

fun validateMonto (monto : String) : GastosValidations{
    return when{
        monto.isBlank() -> GastosValidations(false, "Monto requerida")
        monto.toDouble() < 0 -> GastosValidations(false, "Monto debe ser positivo")
        else -> GastosValidations(true)
    }
}

fun formatFechaParaApi(fechaUsuario: String): String {
    val formatterEntrada = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val fecha = LocalDate.parse(fechaUsuario, formatterEntrada)
    return fecha.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

fun formatFechaParaUi(fechaApi: String): String {
    val fecha = LocalDate.parse(fechaApi.substringBefore("T"))
    return fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}