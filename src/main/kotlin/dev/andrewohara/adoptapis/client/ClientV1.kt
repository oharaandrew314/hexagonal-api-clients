package dev.andrewohara.adoptapis.client

import dev.andrewohara.adoptapis.Breed
import dev.andrewohara.adoptapis.Cat
import org.http4k.client.JavaHttpClient
import org.http4k.core.*
import org.http4k.filter.ClientFilters
import java.io.IOException
import java.time.Instant
import java.util.Properties

/**
 * The original API was designed to perfectly satisfy our UI requirements.
 */
class ClientV1(host: String) {

    private val backend = ClientFilters.SetHostFrom(Uri.of(host))
        .then(JavaHttpClient())

    operator fun get(id: Int): Cat? {
        val response = Request(Method.GET, "api/cats")
            .query("cat_id", id.toString())
            .let(backend)

        if (!response.status.successful) throw IOException("Error getting cat: $response")

        val body = response.bodyString()
        if (body.isEmpty()) return null

        val props = Properties()
        body.reader().use { reader ->
            props.load(reader)
        }

        return Cat(
            id = id.toString(),
            name = props.getProperty("name"),
            ownerId = props.getProperty("owner_id"),
            ownerName = props.getProperty("owner_name"),
            brown = props.getProperty("brown") == "1",
            grey = props.getProperty("grey") == "1",
            breed = Breed.valueOf(props.getProperty("breed")),
            appointments = props.getProperty("appointments")
                .split(",")
                .map { Instant.ofEpochSecond(it.toLong()) }
        )
    }
}