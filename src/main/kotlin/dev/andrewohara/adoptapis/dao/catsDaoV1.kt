package dev.andrewohara.adoptapis.dao

import dev.andrewohara.adoptapis.client.ClientV1

/**
 * This CatsDao will translate the schema of the v1 API into
 */
fun CatsDao.Companion.v1(client: ClientV1): CatsDao {
    return CatsDao { id ->
        client[id.toInt()]
    }
}