package dev.andrewohara.adoptapis.dao

import dev.andrewohara.adoptapis.Breed
import dev.andrewohara.adoptapis.Cat
import dev.andrewohara.adoptapis.client.*
import org.http4k.client.JavaHttpClient
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import java.util.*

fun CatsDao.Companion.v2(host: String): CatsDao {
    val http = ClientFilters.SetHostFrom(Uri.of(host))
        .then(JavaHttpClient())

    val client = ClientV2(http)

    return CatsDao { id ->
        val uuid = UUID.fromString(id)

        val cat = client[uuid]
        val appointments = client.getAppointments(uuid)

        cat?.toModel(appointments)
    }
}

fun CatDtoV2.toModel(appointments: List<AppointmentDtoV2>) = Cat(
    id = id.toString(),
    name = name,
    brown = ColourV2.Brown in colours,
    grey = ColourV2.Grey in colours,
    ownerId = owner.id.toString(),
    ownerName = owner.name,
    breed = when(breed) {
        BreedV2.AmericanShortHair -> Breed.american_short_hair
        BreedV2.Persian -> Breed.persian
        BreedV2.MaineCoon -> Breed.maine_coon
        else -> null  // ignore new breeds we don't understand
    },
    appointments = appointments.map { it.time },
    favouriteFood = favouriteFood
)