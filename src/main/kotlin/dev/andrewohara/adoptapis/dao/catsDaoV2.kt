package dev.andrewohara.adoptapis.dao

import dev.andrewohara.adoptapis.Breed
import dev.andrewohara.adoptapis.Cat
import dev.andrewohara.adoptapis.Colour
import dev.andrewohara.adoptapis.client.*
import java.util.*

fun CatsDao.Companion.v2(client: ClientV2) = CatsDao { id ->
    val uuid = UUID.fromString(id)

    val cat = client[uuid] ?: return@CatsDao null
    val appointments = client.getAppointments(uuid)

    Cat(
        id = cat.id.toString(),
        name = cat.name,
        colours = cat.colours.map { when(it) {
            ColourV2.Brown -> Colour.brown
            ColourV2.Grey -> Colour.grey
            ColourV2.Orange -> Colour.orange
            ColourV2.White -> Colour.white
        } }.toSet(),
        ownerId = cat.owner.id.toString(),
        ownerName = cat.owner.name,
        breed = when(cat.breed) {
            BreedV2.AmericanShortHair -> Breed.american_short_hair
            BreedV2.Persian -> Breed.persian
            BreedV2.MaineCoon -> Breed.maine_coon
            else -> null  // ignore new breeds we don't understand
        },
        appointments = appointments.map { it.time },
        favouriteFood = cat.favouriteFood
    )
}