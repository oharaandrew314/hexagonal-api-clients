package dev.andrewohara.adoptapis.dao

import dev.andrewohara.adoptapis.Breed
import dev.andrewohara.adoptapis.Cat
import dev.andrewohara.adoptapis.Colour
import dev.andrewohara.adoptapis.client.BreedV1
import dev.andrewohara.adoptapis.client.ClientV1

fun CatsDao.Companion.v1(client: ClientV1) = CatsDao { id ->
    val cat = client[id.toInt()] ?: return@CatsDao null

    Cat(
        id = id,
        name = cat.name,
        ownerId = cat.ownerId.toString(),
        ownerName = cat.ownerName,
        breed = when(cat.breed) {
            BreedV1.persian -> Breed.persian
            BreedV1.maine_coon -> Breed.maine_coon
            BreedV1.american_short_hair -> Breed.american_short_hair
        },
        colours = let {
            val colours = mutableSetOf<Colour>()
            if (cat.grey) colours += Colour.grey
            if (cat.brown) colours += Colour.brown
            colours.toSet()
        },
        appointments = cat.appointments
    )
}