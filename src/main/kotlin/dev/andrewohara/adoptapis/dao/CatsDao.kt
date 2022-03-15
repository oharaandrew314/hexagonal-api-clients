package dev.andrewohara.adoptapis.dao

import dev.andrewohara.adoptapis.Cat

/**
 * In order to insulate our app from
 */
fun interface CatsDao {
    operator fun get(id: String): Cat?

    companion object
}