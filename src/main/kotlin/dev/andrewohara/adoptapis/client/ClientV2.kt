package dev.andrewohara.adoptapis.client

import org.http4k.client.JavaHttpClient
import org.http4k.core.*
import org.http4k.filter.ClientFilters
import org.http4k.format.Moshi.auto
import org.http4k.lens.Path
import org.http4k.lens.uuid
import java.io.IOException
import java.time.Instant
import java.util.UUID

/**
 * This new API follows RESTful design principles, but it has some breaking changes compared to the legacy API.
 * We'll need a layer in between the client and UI in order to translate to a common model.
 *
 * Supports the new "favouriteFood" parameter we want to display.
 */
class ClientV2(host: String) {

    private val backend = ClientFilters.SetHostFrom(Uri.of(host))
        .then(JavaHttpClient())

    object Lenses {
        val catId = Path.uuid().of("id")

        val cat = Body.auto<CatDtoV2>().toLens()
        val appointments = Body.auto<List<AppointmentDtoV2>>().toLens()
    }

    operator fun get(catId: UUID): CatDtoV2? {
        val response = Request(Method.GET, "/v2/cats/${Lenses.catId}")
            .with(Lenses.catId of catId)
            .let(backend)

        return when(response.status) {
            Status.OK -> Lenses.cat(response)
            Status.NOT_FOUND -> null
            else -> throw IOException("Error getting cat: $response")
        }
    }

    fun getAppointments(catId: UUID): List<AppointmentDtoV2> {
        val response = Request(Method.GET, "/v2/cats/${Lenses.catId}/appointments")
            .with(Lenses.catId of catId)
            .let(backend)

        return when(response.status) {
            Status.OK -> Lenses.appointments(response)
            else -> throw IOException("Error getting appoints for cat: $catId")
        }
    }
}

data class CatDtoV2(
    val id: UUID,
    val name: String,
    val owner: OwnerDtoV2,
    val colours: Set<ColourV2>,
    val breed: BreedV2,
    val favouriteFood: String
)

data class OwnerDtoV2(
    val id: UUID,
    val name: String
)

data class AppointmentDtoV2(
    val id: UUID,
    val time: Instant,
    val ownerId: UUID,
    val catId: UUID
)

enum class BreedV2 { AmericanShortHair, Persian, MaineCoon, BritishShortHair, Abyssinian, Bengal }
enum class ColourV2 { Brown, Grey, Orange, White }