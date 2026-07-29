package edu.ucne.jorge_moya_ap2_p2.domain.usecase

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle

data class GastosValidations(
    val isValid : Boolean,
    val error : String? = null
)

private val FECHA_FORMATTER: DateTimeFormatter = DateTimeFormatter
    .ofPattern("dd-MM-yyyy")
    .withResolverStyle(ResolverStyle.STRICT)

fun validateFecha (fecha : String) : GastosValidations{
    return when{
        fecha.isBlank() -> GastosValidations(false, "Fecha requerida")
        !fecha.matches(Regex("""^\d{2}-\d{2}-\d{4}$""")) ->
            GastosValidations(false, "Formato de fecha inválido, use dd-MM-yyyy")
        else -> try {
            LocalDate.parse(fecha, FECHA_FORMATTER)
            GastosValidations(true)
        } catch (e: DateTimeParseException) {
            GastosValidations(false, "Fecha inválida")
        }
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