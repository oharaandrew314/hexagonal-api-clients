package dev.andrewohara.adoptapis.dao

import dev.andrewohara.adoptapis.Breed
import dev.andrewohara.adoptapis.Cat
import dev.andrewohara.adoptapis.client.BreedV1
import dev.andrewohara.adoptapis.client.CatDtoV1
import dev.andrewohara.adoptapis.client.ClientV1
import org.http4k.client.JavaHttpClient
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters

/**
 * This CatsDao will translate the schema of the v1 API into
 */
fun CatsDao.Companion.v1(host: String): CatsDao {
    val http = ClientFilters.SetBaseUriFrom(Uri.of(host))
        .then(JavaHttpClient())

    val client = ClientV1(http)

    return CatsDao { id ->
        val dto = client[id.toInt()]
        dto?.toModel()
    }
}

fun CatDtoV1.toModel() = Cat(
    id = id.toString(),
    name = name,
    brown = brown,
    grey = grey,
    ownerId = ownerId.toString(),
    ownerName = ownerName,
    breed = when(breed) {
        BreedV1.persian -> Breed.persian
        BreedV1.american_short_hair -> Breed.american_short_hair
        BreedV1.maine_coon -> Breed.maine_coon
        null -> null
    },
    appointments = appointments,
    favouriteFood = null
)