package dev.andrewohara.adoptapis.client

import org.http4k.core.*
import java.io.IOException
import java.time.Instant
import java.util.Properties

/**
 * The original API was designed to perfectly satisfy our UI requirements.
 */
class ClientV1(private val backend: HttpHandler) {

    operator fun get(id: Int): CatDtoV1? {
        val response = Request(Method.GET, "cats")
            .query("cat_id", id.toString())
            .let(backend)

        if (!response.status.successful) throw IOException("Error getting cat: $response")

        val body = response.bodyString()
        if (body.isEmpty()) return null

        val props = Properties()
        body.reader().use { reader ->
            props.load(reader)
        }

        return CatDtoV1(
            id = id,
            name = props.getProperty("name"),
            ownerId = props.getProperty("owner_id").toInt(),
            ownerName = props.getProperty("owner_name"),
            brown = props.getProperty("brown") == "1",
            grey = props.getProperty("grey") == "1",
            breed = props.getProperty("breed")?.let { BreedV1.valueOf(it) },
            appointments = props.getProperty("appointments")
                .split(",")
                .map { Instant.ofEpochSecond(it.toLong()) }
        )
    }
}

/**
 * This Schema was designed to satisfy the requirements for our internal model.
 * The v1 client could return it directly, but we've made an explicit V1 DTO to follow hexagonal architecture.
 * This decouples the various API schemas from our internal model, and allows our model to evolve independently.
 */
data class CatDtoV1(
    val id: Int,
    val name: String,
    val ownerId: Int,
    val ownerName: String,
    val brown: Boolean,
    val grey: Boolean,
    val breed: BreedV1?,
    val appointments: List<Instant>
)

enum class BreedV1 { persian, american_short_hair, maine_coon }