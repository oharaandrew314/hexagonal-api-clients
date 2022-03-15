package dev.andrewohara.adoptapis

import java.time.Instant

/**
 * We designed this model to hold everything our UI requires.
 */
data class Cat(
    val id: String,
    val name: String,
    val ownerId: String,
    val ownerName: String,
    val brown: Boolean,
    val grey: Boolean,
    val breed: Breed?,
    val appointments: List<Instant>
)

enum class Breed { persian, american_short_hair, maine_coon }