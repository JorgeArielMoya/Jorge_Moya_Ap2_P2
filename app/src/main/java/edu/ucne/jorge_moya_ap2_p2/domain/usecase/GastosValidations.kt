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
    .ofPattern("dd-MM-uuuu")
    .withResolverStyle(ResolverStyle.STRICT)

fun validateFecha (fecha : String) : GastosValidations{
    val normalizada = fecha.trim()
    return when{
        normalizada.isBlank() -> GastosValidations(false, "Fecha requerida")
        !normalizada.matches(Regex("""^\d{2}-\d{2}-\d{4}$""")) ->
            GastosValidations(false, "Formato de fecha inválido, use dd-MM-yyyy")
        else -> try {
            LocalDate.parse(normalizada, FECHA_FORMATTER)
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
    val valor = itbis.trim().toDoubleOrNull()
    return when{
        itbis.isBlank() -> GastosValidations(false, "Itbis requerido")
        valor == null -> GastosValidations(false, "Itbis debe ser un número válido")
        valor < 0 -> GastosValidations(false, "Itbis debe ser positivo")
        else -> GastosValidations(true)
    }
}

fun validateMonto (monto : String) : GastosValidations{
    val valor = monto.trim().toDoubleOrNull()
    return when{
        monto.isBlank() -> GastosValidations(false, "Monto requerido")
        valor == null -> GastosValidations(false, "Monto debe ser un número válido")
        valor < 0 -> GastosValidations(false, "Monto debe ser positivo")
        else -> GastosValidations(true)
    }
}

fun formatFechaParaApi(fechaUsuario: String): String {
    val fecha = LocalDate.parse(fechaUsuario.trim(), FECHA_FORMATTER)
    return fecha.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

fun formatFechaParaUi(fechaApi: String): String {
    val fecha = LocalDate.parse(fechaApi.substringBefore("T"))
    return fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}