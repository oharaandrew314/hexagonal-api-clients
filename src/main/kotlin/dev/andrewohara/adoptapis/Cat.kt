package dev.andrewohara.adoptapis

import java.time.Instant

data class Cat(
    val id: String,
    val name: String,
    val ownerId: String,
    val ownerName: String,
    val colours: Set<Colour>,
    val breed: Breed?,
    val appointments: List<Instant>,
    val favouriteFood: String? = null
)

enum class Breed { persian, american_short_hair, maine_coon }
enum class Colour { brown, grey, orange, white }